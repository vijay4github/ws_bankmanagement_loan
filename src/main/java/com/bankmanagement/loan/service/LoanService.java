package com.bankmanagement.loan.service;

import com.bankmanagement.loan.dto.LoanDTO;
import com.bankmanagement.loan.dto.ResponseDTO;
import com.bankmanagement.loan.entity.Loan;
import com.bankmanagement.loan.mapper.LoanMapper;
import com.bankmanagement.loan.properties.LoanProperties;
import com.bankmanagement.loan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bankmanagement.loan.constant.LoanConstant.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final LoanProperties loanProperties;

    public List<LoanDTO> getLoanDetailByCustomerId(Integer customerId) {
        List<Loan> loans = loanRepository.findByCustomerId(customerId);
        return loans
                .stream()
                .map(loanMapper::loanToLoanDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseDTO processLoan(LoanDTO loanDTO) {
        List<Loan> loans = loanRepository.findByCustomerId(loanDTO.getCustomerId());
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<Integer> pendingLoanId = loans.stream()
                .filter(loan -> PENDING.equalsIgnoreCase(loan.getLoanStatus()))
                .map(Loan::getLoanId)
                .findFirst();
        if (pendingLoanId.isPresent()) {
            responseDTO.setStatus(FAILURE);
            responseDTO.setMessage("Sorry. Previous Loan Already in PENDING Status");
        } else {
            loanDTO.setRateOfInterest(enrichRateOfInterest(loanDTO.getRateOfInterest(), loanDTO.getLoanDuration()));
            Loan loan = loanMapper.loanDTOToLoan(loanDTO);
            loanRepository.save(loan);
            responseDTO.setStatus(SUCCESS);
            responseDTO.setMessage("Thank You. Loan Process submitted for Duration of " +
                    loanDTO.getLoanDuration() + " months, with Interest rate of " +
                    loan.getRateOfInterest() + " %");
        }
        return responseDTO;
    }

    public LoanDTO getLoanDetailByLoanId(Integer loanId) {
        Optional<Loan> loanOptional = loanRepository.findByLoanId(loanId);
        return loanOptional.map(loanMapper::loanToLoanDTO).orElse(null);
    }

    private BigDecimal enrichRateOfInterest(BigDecimal rateOfInterest, Integer loanPeriod) {

        BigDecimal calculatedInterestRate = rateOfInterest;
        if (null == rateOfInterest || rateOfInterest.compareTo(BigDecimal.ZERO) == 0) {
            if (loanPeriod > 0 && loanPeriod < 11) {
                calculatedInterestRate = loanProperties.getPercentage().get(10);
            } else if (loanPeriod >= 11 && loanPeriod < 21) {
                calculatedInterestRate = loanProperties.getPercentage().get(20);
            } else if (loanPeriod >= 21 && loanPeriod < 31) {
                calculatedInterestRate = loanProperties.getPercentage().get(30);
            } else {
                calculatedInterestRate = loanProperties.getPercentage().get(40);
            }
        }
        return calculatedInterestRate;
    }
}