package com.example.accounts;

import com.example.accounts.Constants.AccountsConstants;
import com.example.accounts.DTO.CustomerDto;
import com.example.accounts.Repository.AccountsRepository;
import com.example.accounts.Repository.CustomerRepository;
import com.example.accounts.entity.Accounts;
import com.example.accounts.entity.Customer;
import com.example.accounts.exception.CustomerAlreadyExistsException;
import com.example.accounts.exception.ResourceNotFoundException;
import com.example.accounts.service.Impl.AccountsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AccountApplicationTests {


    @InjectMocks
    private AccountsServiceImpl service;

    @Mock
    private AccountsRepository AccountRepo;

    @Mock
    private CustomerRepository CustomerRepo;

    private CustomerDto CustomerDto;
    private Customer customer;
    private Accounts accounts;

    @BeforeEach
    void setUp() {
        CustomerDto = new CustomerDto();
        CustomerDto.setMobileNumber("9999999999");
        CustomerDto.setName("John Doe");

        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setMobileNumber("9999999999");
        customer.setName("John Doe");

        accounts = new Accounts();
        accounts.setCustomerId(1L);
        accounts.setAccountNumber(1234567890L);
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setBranchAddress(AccountsConstants.ADDRESS);
    }

    @Test
    public void testCreateAccountwithExistingCustomer() {
        // Add test logic here
        when(CustomerRepo.findByMobileNumber(CustomerDto.getMobileNumber())).thenReturn(Optional.of(customer));
        assertThrows(CustomerAlreadyExistsException.class, () -> service.createAccount(CustomerDto));
        // Add assertions and verifications

        verify(CustomerRepo, times(1)).findByMobileNumber("9999999999");
        verify(CustomerRepo, never()).save(any());
        verify(CustomerRepo, never()).save(any());
    }

    @Test
    public void testCreateAccountwithNewCustomer() {
        // Add test logic here
        when(CustomerRepo.findByMobileNumber(CustomerDto.getMobileNumber())).thenReturn(Optional.empty());
        when(CustomerRepo.save(any(Customer.class))).thenReturn(customer);
        when(AccountRepo.save(any(Accounts.class))).thenReturn(accounts);

        service.createAccount(CustomerDto);
        // Add assertions and verifications

        verify(CustomerRepo, times(1)).findByMobileNumber("9999999999");
        verify(CustomerRepo, times(1)).save(any());
        verify(AccountRepo, times(1)).save(any());
    }

    //fetch account
    @Test
    public void testFetchAccountWithValidMobileNumber() {
        when(CustomerRepo.findByMobileNumber("9999999999")).thenReturn(Optional.of(customer));
        when(AccountRepo.findByCustomerId(1L)).thenReturn(Optional.of(accounts));
        CustomerDto act = service.fetchAccount("9999999999");

        assertNotNull(act);
        assertEquals("9999999999", act.getMobileNumber());
        assertEquals(accounts.getAccountNumber(), act.getAccountsDto().getAccountNumber());
    }

    @Test
    void fetchAccountWhenCustomerNotFound() {
        when(CustomerRepo.findByMobileNumber("9999999999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.fetchAccount("9999999999"));

        verify(CustomerRepo, times(1)).findByMobileNumber("9999999999");
        verify(AccountRepo, never()).findByCustomerId(anyLong());
    }

    @Test
    void fetchCustomerWhenAccountNotFound() {
        when(CustomerRepo.findByMobileNumber("9999999999")).thenReturn(Optional.of(customer));
        when(AccountRepo.findByCustomerId(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.fetchAccount("9999999999"));

        verify(CustomerRepo, times(1)).findByMobileNumber("9999999999");
        verify(AccountRepo, times(1)).findByCustomerId(1L);
    }

    @Test
    void deleteAccountWhenCustomerNotFound(){
        when(CustomerRepo.findByMobileNumber("9999999999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteAccount("9999999999"));
    }
}