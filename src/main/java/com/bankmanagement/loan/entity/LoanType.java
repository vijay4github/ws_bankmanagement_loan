package com.bankmanagement.loan.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "bankmanagement")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class LoanType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "LoanTypeCode", nullable = false, unique = true)
    private String loanTypeCode;

    @Column(name = "LoanTypeName", nullable = false)
    private String loanTypeName;
}