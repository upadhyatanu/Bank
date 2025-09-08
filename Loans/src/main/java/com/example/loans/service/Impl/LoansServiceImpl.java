package com.example.loans.service.Impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loans.constants.LoansConstants;
import com.example.loans.dto.LoansDto;
import com.example.loans.entity.Loans;
import com.example.loans.repository.LoansRepository;
import com.example.loans.service.ILoansService;
import com.example.loans.exceptions.ResourceNotFoundException;
import com.example.loans.mapper.LoansMapper;
import com.example.loans.exceptions.LoanAlreadyExistsException;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service

@RequiredArgsConstructor
public class LoansServiceImpl implements ILoansService {
	
	
	private final LoansRepository loansRepository;

	@Override
	public void createLoan(String mobileNumber) {
		// TODO Auto-generated method stub
		
		Optional<Loans> optionalLoans=loansRepository.findByMobileNumber(mobileNumber);
		if(optionalLoans.isPresent()) {
			throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
		}
		
		loansRepository.save(createNewLoan(mobileNumber));
	}

	private Loans createNewLoan(String mobileNumber) {
		
		
		// TODO Auto-generated method stub
		 Loans newLoan = new Loans();
	        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
	        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
	        newLoan.setMobileNumber(mobileNumber);
	        newLoan.setLoanType(LoansConstants.HOME_LOAN);
	        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
	        newLoan.setAmountPaid(0);
	        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
	        return newLoan;
	}

	@Override
	public LoansDto fetchLoan(String mobileNumber) {
		Optional<Loans> optioanLoan=loansRepository.findByMobileNumber(mobileNumber);
		
		if(optioanLoan.isEmpty()) {
			throw new ResourceNotFoundException("Loan","mobile number",mobileNumber);
		}
		return LoansMapper.mapToLoansDto(optioanLoan.get(), new LoansDto());
	}

	@Override
	public boolean updateLoan(LoansDto loansDto) {
	
		Loans loan= loansRepository.findByLoanNumber(loansDto.getLoanNumber())
				.orElseThrow(()->new ResourceNotFoundException("Loan","mobile number",loansDto.getMobileNumber()));
		
		  if (loansDto.getOutstandingAmount()!=(loansDto.getTotalLoan() - loansDto.getAmountPaid())) {
		        throw new IllegalArgumentException(
		            "Outstanding amount must be equal to Total Loan - Amount Paid"
		        );
		    }
		LoansMapper.mapToLoans(loansDto, loan);
		
		loansRepository.save(loan);
		return true;
	}

	@Override
	public boolean deleteLoan(String mobileNumber) {
		// TODO Auto-generated method stub
		 Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
	                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
	        );
	        loansRepository.deleteById(loans.getLoanId());
	        return true;
	}

}
