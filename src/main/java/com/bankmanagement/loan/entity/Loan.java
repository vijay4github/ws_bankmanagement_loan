package com.bankmanagement.loan.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(schema = "bankmanagement")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Loan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loanId;

    @NotNull(message = "Loan Amount Must Not be Empty")
    @Column(name = "LoanAmount", nullable = false)
    private BigDecimal loanAmount;

    @NotEmpty(message = "Loan Type Must Not be Empty")
    @Column(name = "LoanTypeCode", nullable = false)
    private String loanTypeCode;

    @NotNull(message = "Rate Of Interest Must Not be Empty")
    @Column(name = "RateOfInterest", nullable = false)
    private BigDecimal rateOfInterest;

    @NotNull(message = "Loan Duration Must Not be Empty")
    @Column(name = "LoanDuration", nullable = false)
    private Integer loanDuration;

    @NotNull(message = "Customer Id Must Not be Empty")
    @Column(name = "CustomerId", nullable = false)
    private Integer customerId;

    @Column(name = "LoanStatus")
    private String loanStatus;

    @CreationTimestamp
    @Column(name = "CreateTimeStamp")
    private LocalDateTime createTimeStamp;

    @UpdateTimestamp
    @Column(name = "UpdateTimeStamp")
    private LocalDateTime updateTimeStamp;
}
