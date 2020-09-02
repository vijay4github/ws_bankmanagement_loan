package com.bankmanagement.loan.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanDTO {

    private Integer loanId;

    private BigDecimal loanAmount;

    private String loanTypeCode;

    private BigDecimal rateOfInterest;

    private Integer loanDuration;

    private Integer customerId;

    private String loanStatus;
}
