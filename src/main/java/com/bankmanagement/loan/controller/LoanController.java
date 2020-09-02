package com.bankmanagement.loan.controller;

import com.bankmanagement.loan.dto.LoanDTO;
import com.bankmanagement.loan.dto.ResponseDTO;
import com.bankmanagement.loan.service.LoanService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoanController {
    private final LoanService loanService;

    @ApiOperation(value = "Service to fetch the Loan details based on Loan Id")
    @GetMapping("/loan/{loanId}")
    public ResponseEntity<LoanDTO> loanDetailByLoanId(@PathVariable("loanId") Integer loanId) {
        log.info("Request received to get Loan Detail for Loan Id {}", loanId);
        return new ResponseEntity<>(loanService.getLoanDetailByLoanId(loanId), HttpStatus.OK);
    }

    @ApiOperation(value = "Service to fetch the Loan details belongs to Customer Id")
    @GetMapping("/loan/customer/{customerId}")
    public ResponseEntity<List<LoanDTO>> loanDetailByCustomerId(@PathVariable("customerId") Integer customerId) {
        log.info("Request received to get customerId Id {}", customerId);
        return new ResponseEntity<>(loanService.getLoanDetailByCustomerId(customerId), HttpStatus.OK);
    }

    @ApiOperation(value = "Service Create Loan for Customer")
    @PostMapping("/loan")
    public ResponseEntity<ResponseDTO> loanProcessing(@RequestBody LoanDTO loanDTO) {
        log.info("Request to Create the Loan for customerId Id {}", loanDTO.getCustomerId());
        ResponseDTO responseDTO = loanService.processLoan(loanDTO);
        if ("SUCCESS".equals(responseDTO.getMessage())) {
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
    }

}
