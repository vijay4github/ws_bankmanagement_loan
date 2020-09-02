package com.bankmanagement.loan.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDTO {

    private String message;

    private String status;

    private List<String> errors;
}
