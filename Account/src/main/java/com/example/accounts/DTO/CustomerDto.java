package com.example.accounts.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

@Schema(
		name="Customer",
		description="Customer details"
		)
public class CustomerDto {
	
	@Schema(			
			description="Customer name"	
			)
	

	@NotEmpty(message="name cannot be null or empty")
	@Size(min=5,max=25,message="name must be between 5 and 25 characters")
	private String name;
	
	
	@Schema(			
			description="Customer email"	
			)
	@NotEmpty(message="name cannot be null or empty")
	@Email(message="please enter valid email")
	private String email;
	
	@Schema(			
			description="Customer mobile number"	
			)
	 @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	private String mobileNumber;
	
	
	
	private AccountsDto accountsDto;
}
