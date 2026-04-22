package com.example.featuretoggle.security;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
		info = @Info(
				title = "Feature Toggle Management API",
				version = "1.0",
				description = "API to manage feature toggles across environments",
				contact = @Contact(
						name = "Arpitha R ",
						email = "arpitha.r3@tcs.com"
						)
				),
		security = @SecurityRequirement(name="basicAuth")
		)

@SecurityScheme(
		name = "basicAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "basic"
		)

public class OpenApiConfig {

}
