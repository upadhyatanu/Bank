package com.example.loans.controllerTest;

import com.example.loans.controller.LoansController;
import com.example.loans.dto.LoansDto;
import com.example.loans.service.ILoansService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import static org.junit.jupiter.api.extension.MediaType.APPLICATION_JSON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoansController.class)
@AutoConfigureMockMvc
public class TestController {

    @MockitoBean
    ILoansService iLoansService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
   public void getLoanDetails_shouldReturn200AndJson_whenMobileValid() throws Exception {
       when(iLoansService.fetchLoan("1234567890")).thenReturn(new LoansDto());

       mockMvc.perform(get("/loans/fetch")
               .param("mobileNumber", "1234567890"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)));

   }

   @Test
   public void createLoan_shouldReturnResponseJson_whenMobileValid() throws Exception {
       // Test implementation for createLoan endpoint

       doNothing().when(iLoansService).createLoan("1234567890");

       mockMvc.perform(post("/loans/create")
               .param("mobileNumber", "1234567890"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.statusCode").exists());
   }

   @Test
   public void createLoan_shouldReturn500_whenMobileInvalid() throws Exception {
       // Test implementation for createLoan endpoint with invalid mobile number

         mockMvc.perform(post("/loans/create")
                .param("mobileNumber", "123"))
                .andExpect(status().isInternalServerError());
   }

   @Test
    public void updateLoan_shouldReturn200_whenMobileValid() throws Exception {
        // Test implementation for updateLoan endpoint

       LoansDto loansDto= new LoansDto();
       loansDto.setLoanNumber("123456789012");
       loansDto.setMobileNumber("9876543210");
       loansDto.setLoanType("Home Loan");
       loansDto.setTotalLoan(500000);
       loansDto.setAmountPaid(100000);
       loansDto.setOutstandingAmount(400000);

       when(iLoansService.updateLoan(loansDto)).thenReturn(true);

        mockMvc.perform(put("/loans/update")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loansDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").exists());
    }

    @Test
    public void updateLoan_ShouldReturn400_whenInputInvalid() throws Exception {
        // Test implementation for updateLoan endpoint with invalid input

        LoansDto loansDto= new LoansDto();
        loansDto.setLoanNumber("123"); // Invalid loan number
        loansDto.setMobileNumber("9876543210");
        loansDto.setLoanType("Home Loan");
        loansDto.setTotalLoan(500000);
        loansDto.setAmountPaid(100000);
        loansDto.setOutstandingAmount(400000);

        mockMvc.perform(put("/loans/update")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loansDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateLoan_shouldReturn417_whenNotUpdated() throws Exception {
        // Test implementation for updateLoan endpoint when update fails

        LoansDto loansDto= new LoansDto();
        loansDto.setLoanNumber("123456789012");
        loansDto.setMobileNumber("9876543210");
        loansDto.setLoanType("Home Loan");
        loansDto.setTotalLoan(500000);
        loansDto.setAmountPaid(100000);
        loansDto.setOutstandingAmount(400000);

        when(iLoansService.updateLoan(loansDto)).thenReturn(false);

        mockMvc.perform(put("/loans/update")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loansDto)))
                .andExpect(status().isExpectationFailed())
                .andExpect(jsonPath("$.statusCode").exists());
    }

    @Test
    void createLoan_shouldReturn400_whenMobileInvalid() throws Exception {
        mockMvc.perform(post("/loans/create")
                        .param("mobileNumber", "abc"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void deleteLoan_shouldReturn200_whenDeleted() throws Exception {
        when(iLoansService.deleteLoan("1234567890")).thenReturn(true);

        mockMvc.perform(delete("/loans/delete")
                        .param("mobileNumber", "1234567890"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").exists())
                .andExpect(jsonPath("$.statusMessage").exists());
    }


}
