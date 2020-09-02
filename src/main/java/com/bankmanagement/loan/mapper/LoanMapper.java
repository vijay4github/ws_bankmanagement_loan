package com.bankmanagement.loan.mapper;

import com.bankmanagement.loan.dto.LoanDTO;
import com.bankmanagement.loan.entity.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {
    public LoanDTO loanToLoanDTO(Loan loan) {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setCustomerId(loan.getCustomerId());
        loanDTO.setLoanId(loan.getLoanId());
        loanDTO.setLoanAmount(loan.getLoanAmount());
        loanDTO.setLoanDuration(loan.getLoanDuration());
        loanDTO.setLoanTypeCode(loan.getLoanTypeCode());
        loanDTO.setRateOfInterest(loan.getRateOfInterest());
        loanDTO.setLoanStatus(loan.getLoanStatus());
        return loanDTO;
    }

    public Loan loanDTOToLoan(LoanDTO loanDTO) {
        Loan loan = new Loan();
        loan.setCustomerId(loanDTO.getCustomerId());
        loan.setLoanId(loanDTO.getLoanId());
        loan.setLoanAmount(loanDTO.getLoanAmount());
        loan.setLoanDuration(loanDTO.getLoanDuration());
        loan.setLoanTypeCode(loanDTO.getLoanTypeCode());
        loan.setRateOfInterest(loanDTO.getRateOfInterest());
        loan.setLoanStatus("PENDING");
        return loan;
    }
}
