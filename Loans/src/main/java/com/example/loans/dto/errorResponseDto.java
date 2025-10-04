package com.example.loans.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

//DTO class to hold error response
@Schema(name="errorResponse" , description="schema to hold error response info")
@Data @AllArgsConstructor
public class errorResponseDto {
	
	//schema is used for swagger documentation purpose.

	@Schema(description="api path invoked by client")
	private String apiPath;
	
	@Schema(description="error code representing error happened")
	private HttpStatus errorCode;
	
	@Schema(description="error message represeting error happened")
	private String errorMessage;
	
	@Schema(description="error time at which error happenned")
	private LocalDateTime errorTime;
}
