package com.example.featuretoggle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record FeatureCreateRequest(

		@NotBlank(message = "Feature name is required")
	 String featureName,
	
	// @NotNull(message = "Environment is required")
	// Environment environment, 
	 @NotBlank(message="Environment is required")
		@Schema(description = "Deployment environment", allowableValues = {"DEV", "QA", "PROD"}) // Fixes Swagger docs
		@Pattern(regexp = "^(DEV|QA|PROD)$",message="Environmet must be DEV , QA or PROD")
	 String environment, // Changed to String to prevent Jackson parsing crashes
	
	@NotNull(message = "Enabled flag is required")
	 Boolean enabled
		) {
}
