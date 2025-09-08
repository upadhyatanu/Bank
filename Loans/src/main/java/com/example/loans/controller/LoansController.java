package com.example.loans.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.example.loans.constants.LoansConstants;
import com.example.loans.dto.LoansDto;
import com.example.loans.dto.ResponseDto;
import com.example.loans.dto.errorResponseDto;
import com.example.loans.service.ILoansService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@Tag(
        name = "CRUD REST APIs for Loans in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path="/loans",produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class LoansController {
	
	
	private final ILoansService iLoansService;

	@Operation(summary="fetach loan details", description = "REST API to fetch loan details based on a mobile number")
	@ApiResponses({
		@ApiResponse(responseCode="200",description="Loan details fetched successfully"),
		@ApiResponse(responseCode="500",description="Internale error",content = @Content(
                schema = @Schema(implementation = errorResponseDto.class)
        ))
	})
	
	@GetMapping("/fetch")
	public ResponseEntity<LoansDto> getLoanDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
		
		LoansDto loansdto=iLoansService.fetchLoan(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(loansdto);
	}
	
	
	  @Operation(
	            summary = "Create Loan REST API",
	            description = "REST API to create new loan inside EazyBank"
	    )
	    @ApiResponses({
	            @ApiResponse(
	                    responseCode = "201",
	                    description = "HTTP Status CREATED"
	            ),
	            @ApiResponse(
	                    responseCode = "500",
	                    description = "HTTP Status Internal Server Error",
	                    content = @Content(
	                            schema = @Schema(implementation = errorResponseDto.class)
	                    )
	            )
	    }
	    )
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createLoan(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber){
		iLoansService.createLoan(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
	}
	  
	  
	  @Operation(summary="update the Loan details", description="REST API to update loan details based on loan number")
	  @ApiResponses({
		  @ApiResponse(responseCode="200",description="Loan details updated successfully"),
		  @ApiResponse(responseCode="417",description="Expectation failed"),
		  @ApiResponse(responseCode="500",description="Internale error",content = @Content(
			  schema=@Schema(implementation=errorResponseDto.class)
		  ))
	  })
	  
	  @PutMapping("/update")
	  public ResponseEntity<ResponseDto> updateLoan(@Valid @RequestBody LoansDto loansDto){
	
		  boolean isupdated=iLoansService.updateLoan(loansDto);
		  
		  if(isupdated)
		  return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
		  else
		  return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
	  }
	  
	  
	  @Operation(summary="delete the Loan details", description="REST API to delete loan details based on mobile number")
	  @ApiResponses({
		  @ApiResponse(responseCode="200", description="Loan details deleted successfully"),
		  @ApiResponse(responseCode="417",description="Expectation failed"),
		  @ApiResponse(responseCode="500",description="Internale error",content = @Content(
			  schema=@Schema(implementation=errorResponseDto.class)
		  ))
	  })
	  @DeleteMapping("/delete")
	  public ResponseEntity<ResponseDto> deleteLoan(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber){
		  
		  boolean isdeleted= iLoansService.deleteLoan(mobileNumber);
		  
		  if(isdeleted)
			  return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
		  else
			  return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
	  }
	  
	  
}
