package com.example.accounts.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor


@Schema(	
		name="Response",
		description="schema to hold succesful response"	
		)
public class ResponseDto {

	
	@Schema(			
			description="status code in response", example="200"	
			)
	private String statusCode;
	
	@Schema(			
			description="status message in response", example="Request posted succesfully"	
			)
	private String statusMsg;
	
}
