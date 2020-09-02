package com.bankmanagement.loan.service;

import com.bankmanagement.loan.dto.LoanDTO;
import com.bankmanagement.loan.dto.ResponseDTO;
import com.bankmanagement.loan.entity.Loan;
import com.bankmanagement.loan.mapper.LoanMapper;
import com.bankmanagement.loan.properties.LoanProperties;
import com.bankmanagement.loan.repository.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    private LoanProperties loanProperties;
    private LoanMapper loanMapper;

    @Before
    public void setup() {
        loanMapper = new LoanMapper();
        loanProperties = new LoanProperties();
        Map<Integer, BigDecimal> percentage = new HashMap<>();
        percentage.put(10,BigDecimal.TEN);
        percentage.put(20,BigDecimal.TEN);
        percentage.put(30,BigDecimal.valueOf(12));
        percentage.put(40,BigDecimal.valueOf(13));
        loanProperties.setPercentage(percentage);
        loanService = new LoanService(loanRepository, loanMapper, loanProperties);
    }

    @Test
    public void getLoanDetailByCustomerIdTest() {
        when(loanRepository.findByCustomerId(100)).thenReturn(mockLoanDetails());
        log.info("result {}", loanRepository.findByCustomerId(100));
        List<LoanDTO> response = loanService.getLoanDetailByCustomerId(100);
        log.info("Respnse :{}", response);
        assertNotNull(response);
    }

    @Test
    public void getLoanDetailByCustomerIdTest_emptyResult() {
        List<LoanDTO> response = loanService.getLoanDetailByCustomerId(100);
        log.info("Respnse :{}", response);
        assertNotNull(response);
    }

    @Test
    public void getLoanDetailByLoanIdTest() {
        when(loanRepository.findByLoanId(1001)).thenReturn(mockLoan(1001));
        LoanDTO response = loanService.getLoanDetailByLoanId(1001);
        assertNotNull(response);
    }

    @Test
    public void processLoan_Failure(){
        when(loanRepository.findByCustomerId(1231)).thenReturn(mockLoanDetails());
        ResponseDTO responseDTO = loanService.processLoan(mockLoadDTO());
        assertNotNull(responseDTO);
        assertTrue("FAILURE".equals(responseDTO.getStatus()));
    }

    @Test
    public void processLoan_Success(){
        LoanDTO loanDTO = mockLoadDTO();
        loanDTO.setRateOfInterest(BigDecimal.ZERO);
        ResponseDTO responseDTO = loanService.processLoan(loanDTO);
        assertNotNull(responseDTO);
        assertTrue("SUCCESS".equals(responseDTO.getStatus()));
    }

    @Test
    public void processLoan_interestRateCalculation(){
        LoanDTO loanDTO = mockLoadDTO();
        loanDTO.setRateOfInterest(BigDecimal.ZERO);
        ResponseDTO responseDTO = loanService.processLoan(loanDTO);
        loanDTO.setRateOfInterest(BigDecimal.ZERO);
        loanDTO.setLoanDuration(10);
        loanService.processLoan(loanDTO);
        loanDTO.setRateOfInterest(BigDecimal.ZERO);
        loanDTO.setLoanDuration(30);
        loanService.processLoan(loanDTO);
        loanDTO.setRateOfInterest(BigDecimal.ZERO);
        loanDTO.setLoanDuration(40);
        loanService.processLoan(loanDTO);
        assertNotNull(responseDTO);
        assertTrue("SUCCESS".equals(responseDTO.getStatus()));
    }
    private Optional<Loan> mockLoan(int loanId) {
        Loan loan = new Loan();
        loan.setLoanId(loanId);
        loan.setLoanStatus("PENDING");
        loan.setLoanAmount(BigDecimal.valueOf(1000));
        return Optional.ofNullable(loan);
    }

    private List<Loan> mockLoanDetails() {
        Loan loan = new Loan();
        loan.setLoanStatus("PENDING");
        loan.setLoanId(1231);
        return Arrays.asList(loan);
    }
    private LoanDTO mockLoadDTO(){
        LoanDTO loanDTO =new LoanDTO();
        loanDTO.setCustomerId(1231);
        loanDTO.setLoanDuration(20);
        loanDTO.setLoanAmount(BigDecimal.valueOf(12300));
        return loanDTO;
    }

}
