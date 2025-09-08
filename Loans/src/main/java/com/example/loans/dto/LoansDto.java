package com.example.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(
		name="loans",
		description="loans details"
		)

@Data
public class LoansDto {
	
	
	@NotEmpty(message="mobile number cannot be null or empty")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	@Schema(description="mobile number", example="9843212389")
	private String mobileNumber;
	
	
	@NotEmpty(message="loan number cannot be null or empty")
	@Pattern(regexp = "(^$|[0-9]{12})", message = "Loan number must be 12 digits")
	@Schema(description="loan number", example="123465871234")
	private String loanNumber;
	
	
	@NotEmpty(message="loan type cannot be null or empty")
	@Schema(description="loan type", example="home loan")
	private String loanType;
	
	@Positive(message="loan amount cannot be negative")
	@Schema(description="loan amount", example="10000")
	private int totalLoan;
	
	@PositiveOrZero(message="amount paid cannot be negative")
	@Schema(description="amount paid", example="2000")
	private int amountPaid;
	
	@PositiveOrZero(message="outstanding amount cannot be negative")
	@Schema(description="outstanding amount", example="8000")
	private int outstandingAmount;
}
