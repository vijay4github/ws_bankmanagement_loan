package com.bankmanagement.loan.exception;

import com.bankmanagement.loan.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
public class CustomExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ResponseDTO> handleConstraintViolation(
            ConstraintViolationException ex,
            WebRequest request)
    {
        List<String> details = ex.getConstraintViolations()
                .parallelStream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("FAILURE");
        responseDTO.setMessage("Mandatory Fields Missing");
        responseDTO.setErrors(details);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
