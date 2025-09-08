package com.example.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "ResponseDto", description = "Response of the API")
public class ResponseDto {

	@Schema(description = "Status code in response")
	private String statusCode;
	
	@Schema(description = "Status message in response")
	private String statusMessage;
}
