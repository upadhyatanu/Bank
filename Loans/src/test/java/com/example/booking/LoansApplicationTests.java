package com.example.booking;

import com.example.loans.dto.LoansDto;
import com.example.loans.entity.Loans;
import com.example.loans.exceptions.LoanAlreadyExistsException;
import com.example.loans.exceptions.ResourceNotFoundException;
import com.example.loans.repository.LoansRepository;
import com.example.loans.service.Impl.LoansServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class LoansApplicationTests {

    @Mock
    LoansRepository loansRepository;

    @InjectMocks
    LoansServiceImpl loansService;

    private LoansDto loansDto;
    private Loans loan;

    @BeforeEach
    void setup(){
        loansDto=new LoansDto();
        loansDto.setLoanNumber("1234567890");
        loansDto.setMobileNumber("9876543210");
        loansDto.setLoanType("Home Loan");
        loansDto.setTotalLoan(500000);
        loansDto.setAmountPaid(100000);
        loansDto.setOutstandingAmount(400000);

        loan=new Loans();
        loan.setLoanId(1L);
        loan.setLoanNumber("1234567890");
        loan.setMobileNumber("9876543210");
        loan.setLoanType("Home Loan");
        loan.setTotalLoan(500000);
        loan.setAmountPaid(100000);
        loan.setOutstandingAmount(400000);
    }

    @Test
    void createLoanWhenMobileNumberNotPresent() {

        when(loansRepository.findByMobileNumber("9876543210")).thenReturn(Optional.empty());
        when(loansRepository.save(any(Loans.class))).thenReturn(loan);

        loansService.createLoan("9876543210");

        verify(loansRepository,times(1)).save(any(Loans.class));

    }

    @Test
    void createLoanWhenMobileNumberPresent() {
        when(loansRepository.findByMobileNumber("9876543210")).thenReturn(Optional.of(loan));

        assertThrows(LoanAlreadyExistsException.class,()->loansService.createLoan("9876543210"));
    }

    @Test
    void fetchLoanWhenMobileNumberNotPresent() {
        when(loansRepository.findByMobileNumber("9876543210")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,()->loansService.fetchLoan("9876543210"));


    }

    @Test
    void fetchLoanWhenMobileNumberPresent() {
        when(loansRepository.findByMobileNumber("9876543210")).thenReturn(Optional.of(loan));

        LoansDto result=loansService.fetchLoan("9876543210");

        assertNotNull(result);
        assertEquals("9876543210",result.getMobileNumber());
        assertEquals("1234567890",result.getLoanNumber());

        verify(loansRepository,times(1)).findByMobileNumber("9876543210");

    }

    @Test
    void testUpdateLoanWhenLoanNumberPresent() {
        when(loansRepository.findByLoanNumber("1234567890")).thenReturn(Optional.of(loan));
        when(loansRepository.save(any(Loans.class))).thenReturn(loan);
        boolean isUpdated=loansService.updateLoan(loansDto);
        assertTrue(isUpdated);
        verify(loansRepository,times(1)).save(any(Loans.class));
    }

    @Test
    void testUpdateLoanwithIncorrectOutstandingAmount() {
        loansDto.setOutstandingAmount(300000); // Incorrect outstanding amount
        when(loansRepository.findByLoanNumber("1234567890")).thenReturn(Optional.of(loan));
        assertThrows(IllegalArgumentException.class,()->loansService.updateLoan(loansDto));
    }

    @Test
    void testUpdateLoanWhenLoanNumberNotPresent() {
        when(loansRepository.findByLoanNumber("1234567890")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->loansService.updateLoan(loansDto));
    }

    @Test
    void testDeleteLoanWhenMobileNumberPresent() {
       when(loansRepository.findByMobileNumber("9876543210")).thenReturn(Optional.of(loan));
      boolean isDeleted=loansService.deleteLoan("9876543210");
       assertTrue(isDeleted);
       verify(loansRepository,times(1)).deleteById(anyLong());
       verify(loansRepository,times(1)).findByMobileNumber("9876543210");
        verifyNoMoreInteractions(loansRepository);
    }

    @Test
    void testDeleteLoanWhenMobileNumberNotPresent() {
        when(loansRepository.findByMobileNumber("9876543210")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->loansService.deleteLoan("9876543210"));
        verify(loansRepository, times(1)).findByMobileNumber("9876543210");
        verify(loansRepository, never()).deleteById(anyLong());
    }

}
