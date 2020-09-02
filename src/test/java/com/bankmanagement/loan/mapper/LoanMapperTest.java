package com.bankmanagement.loan.mapper;

import com.bankmanagement.loan.dto.LoanDTO;
import com.bankmanagement.loan.entity.Loan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoanMapperTest {

    private LoanMapper loanMapper;

    @Before
    public void setUp(){
        loanMapper = new LoanMapper();
    }

    @Test
    public void loanToLoanDTOTest() {

        //ACT
        LoanDTO loanDTO = loanMapper.loanToLoanDTO(mockLoan());

        //Assert
        assertNotNull(loanDTO);
        assertTrue("PENDING".equals(loanDTO.getLoanStatus()));
        assertTrue(20 == loanDTO.getLoanDuration());

    }

    @Test
    public void loanDTOToLoanTest() {

        //ACT
        Loan loan = loanMapper.loanDTOToLoan(mockLoanDTO());

        //Assert
        assertNotNull(loan);
        assertTrue("HOMELOAN".equals(loan.getLoanTypeCode()));
        assertTrue(25 == loan.getLoanDuration());

    }

    private LoanDTO mockLoanDTO() {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setCustomerId(1002);
        loanDTO.setLoanId(10022);
        loanDTO.setLoanAmount(BigDecimal.valueOf(12000));
        loanDTO.setLoanDuration(25);
        loanDTO.setLoanTypeCode("HOMELOAN");
        return loanDTO;
    }

    private Loan mockLoan() {
        Loan loan = new Loan();
        loan.setCustomerId(121);
        loan.setLoanId(1221);
        loan.setLoanAmount(BigDecimal.valueOf(1200));
        loan.setLoanDuration(20);
        loan.setLoanTypeCode("EDUCATION");
        loan.setRateOfInterest(BigDecimal.valueOf(11.1));
        loan.setLoanStatus("PENDING");
        return loan;
    }
}
