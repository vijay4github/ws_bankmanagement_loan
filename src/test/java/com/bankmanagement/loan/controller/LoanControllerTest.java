package com.bankmanagement.loan.controller;


import com.bankmanagement.loan.dto.LoanDTO;
import com.bankmanagement.loan.dto.ResponseDTO;
import com.bankmanagement.loan.service.LoanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.bankmanagement.loan.constant.LoanConstant.FAILURE;
import static com.bankmanagement.loan.constant.LoanConstant.SUCCESS;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class LoanControllerTest {

    @InjectMocks
    LoanController loanController;

    @Mock
    LoanService loanService;
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(loanController)
                .build();
    }

    @Test
    public void loanDetailByCustomerIdTest() throws Exception {

        when(loanService.getLoanDetailByCustomerId(100)).thenReturn(mockLoadDTOList());
        MockHttpServletResponse response = mvc.perform(
                get("/loan/customer/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        verify(loanService, times(1)).getLoanDetailByCustomerId(100);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void getLoanDetailByLoanIdTest() throws Exception {

        when(loanService.getLoanDetailByLoanId(1001)).thenReturn(mockLoadDTO());
        MockHttpServletResponse response = mvc.perform(
                get("/loan/1001")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        verify(loanService, times(1)).getLoanDetailByLoanId(1001);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void processLoan_Success() throws Exception {

        //ARRANGE
        when(loanService.processLoan(any())).thenReturn(mockResponseDTOSuccess());
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = MockMvcRequestBuilders.post("/loan").content(objectMapper.writeValueAsString(mockLoadDTO()))
                .contentType("application/json");
        //ACT
        MvcResult mvcResult = mvc.perform(request).andReturn();

        //ASSERT
        assertNotNull(mvcResult.getResponse().getContentAsString());
        log.info("Content {}", mvcResult.getResponse().getContentAsString());
        ResponseDTO responseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseDTO.class);
        assertEquals(SUCCESS, responseDTO.getStatus());
        verify(loanService, times(1)).processLoan(any());
    }


    @Test
    public void processLoan_Failure() throws Exception {

        //ARRANGE
        when(loanService.processLoan(any())).thenReturn(mockResponseDTO());
        ObjectMapper objectMapper = new ObjectMapper();
        RequestBuilder request = MockMvcRequestBuilders.post("/loan").content(objectMapper.writeValueAsString(mockLoadDTO()))
                .contentType("application/json");
        //ACT
        MvcResult mvcResult = mvc.perform(request).andReturn();

        //ASSERT
        assertNotNull(mvcResult.getResponse().getContentAsString());
        ResponseDTO responseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseDTO.class);
        assertEquals(FAILURE, responseDTO.getStatus());
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        verify(loanService, times(1)).processLoan(any());
    }

    private ResponseDTO mockResponseDTO() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus(FAILURE);
        responseDTO.setMessage("Sorry.  Previous Loan Already in PENDING Status");
        return responseDTO;
    }

    private ResponseDTO mockResponseDTOSuccess() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus(SUCCESS);
        responseDTO.setMessage("Loan Processed Successfully");
        return responseDTO;
    }

    private List<LoanDTO> mockLoadDTOList() {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setCustomerId(1231);
        loanDTO.setLoanDuration(20);
        loanDTO.setLoanAmount(BigDecimal.valueOf(12300));
        return Arrays.asList(loanDTO);
    }

    private LoanDTO mockLoadDTO() {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setCustomerId(1231);
        loanDTO.setLoanDuration(20);
        loanDTO.setLoanAmount(BigDecimal.valueOf(12300));
        return loanDTO;
    }
}
