package com.example.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")

@OpenAPIDefinition(
		info= @Info(
				title="accounts microservice REST API documentation",
				description="Eazybank accounts microservices REST API documentation",
				version="v1",
				contact= @Contact(
						name="tanushree",
						email="upadhyatanu@gmail.com"
						),
				
				license=@License(
					name="apache 2.0"
				)
				),
		externalDocs=@ExternalDocumentation(
			description="haha bhai bas kar kitna padega"
		)
		
		)
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

}
