package com.example.accounts.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data

@Schema(	
		name="Account",
		description="Account"	
		)
public class AccountsDto {
	
	
	
	@Schema(			
			description="account number"	
			)
	 @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits")
	private Long accountNumber;
	
	
	@Schema(			
			description="type of account"	
			)
	@NotEmpty(message="account type cannot be null or empty")
	private String accountType;
	
	
	@Schema(			
			description="Accont branch address"	
			)
	@NotEmpty(message="branch address cannot be null or empty")
	private String branchAddress;

}
