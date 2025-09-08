package com.example.accounts.service;

import com.example.accounts.DTO.CustomerDto;

public interface IAccountsService {

	void createAccount(CustomerDto customerdto);
	
	CustomerDto fetchAccount(String mobileNumber);
	
	public boolean updateAccount(CustomerDto customerdto);
	
	public boolean deleteAccount(String mobileNumber);
}
