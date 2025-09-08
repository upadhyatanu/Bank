package com.example.accounts.DTO;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(	
		name="ErrorResponse",
		description="schema to hold error response"	
		)
public class ErrorResponseDto {

	@Schema(	
			
			description="status code of error "	
			)
	private HttpStatus statusCode;
	
	@Schema(	
			
			description="error message"	
			)
	private String errorMsg;
	
	@Schema(	
			
			description="api path invoked by client"	
			)
	private String apiPath;
	private LocalDateTime errorTime;
}
