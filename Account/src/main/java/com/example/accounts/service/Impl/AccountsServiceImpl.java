package com.example.accounts.service.Impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.accounts.Constants.AccountsConstants;
import com.example.accounts.DTO.AccountsDto;
import com.example.accounts.DTO.CustomerDto;
import com.example.accounts.Repository.AccountsRepository;
import com.example.accounts.Repository.CustomerRepository;
import com.example.accounts.entity.Accounts;
import com.example.accounts.entity.Customer;
import com.example.accounts.exception.CustomerAlreadyExistsException;
import com.example.accounts.exception.ResourceNotFoundException;
import com.example.accounts.mapper.AccountsMapper;
import com.example.accounts.mapper.CustomerMapper;
import com.example.accounts.service.IAccountsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

	private AccountsRepository accountsrepository;
	private CustomerRepository customerrepository;
	
	@Override
	public void createAccount(CustomerDto customerdto) {
		
		Optional<Customer> optionalcustomer=customerrepository.findByMobileNumber(customerdto.getMobileNumber());
		if(optionalcustomer.isPresent()) {
			throw new CustomerAlreadyExistsException("Customer is already registres with this mobile number "+customerdto.getMobileNumber());
		}
		
		Customer customer=CustomerMapper.mapToCustomer(customerdto, new Customer());
		
		Customer savedcustomer=customerrepository.save(customer);
		accountsrepository.save(createNewAccount(savedcustomer));
	}
	
	 private Accounts createNewAccount(Customer customer) {
	        Accounts newAccount = new Accounts();
	        newAccount.setCustomerId(customer.getCustomerId());
	        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

	        newAccount.setAccountNumber(randomAccNumber);
	        newAccount.setAccountType(AccountsConstants.SAVINGS);
	        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
	      
			
	        return newAccount;
	    }

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		Customer customer=customerrepository.findByMobileNumber(mobileNumber).orElseThrow(
				()-> new ResourceNotFoundException("Customer","Mobile Number",mobileNumber)
				);
		Accounts accounts=accountsrepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				()-> new ResourceNotFoundException("Accounts","Mobile Number",customer.getCustomerId().toString())	
				);
		CustomerDto customerdto=CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		customerdto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
		return customerdto;
	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		// TODO Auto-generated method stub
		
		boolean isupdated=false;
		  AccountsDto accountsDto = customerDto.getAccountsDto();
		  if(accountsDto!=null) {
			  Accounts accounts=accountsrepository.findById(accountsDto.getAccountNumber()).orElseThrow(
				  ()-> new ResourceNotFoundException("Accounts","Account Number",accountsDto.getAccountNumber().toString())
			  );
			  AccountsMapper.mapToAccounts(accountsDto, accounts);
	            accounts = accountsrepository.save(accounts);

	            Long customerId = accounts.getCustomerId();
	            Customer customer = customerrepository.findById(customerId).orElseThrow(
	                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
	            );
	            CustomerMapper.mapToCustomer(customerDto,customer);
	            customerrepository.save(customer);
	            isupdated = true;
	        }
			  
		
		return isupdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
			
		
		Customer customer=customerrepository.findByMobileNumber(mobileNumber).orElseThrow(
				()-> new ResourceNotFoundException("Customer","Mobile Number",mobileNumber)
				);
		
		accountsrepository.deleteByCustomerId(customer.getCustomerId());
		customerrepository.deleteById(customer.getCustomerId());
		return true;
		
	}
	 
	 

}
