package com.example.featuretoggle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FeatureCreateRequest(

		@NotBlank(message = "Feature name is required")
	 String featureName,
	
	 @NotNull(message = "Environment is required")
	 Environment environment, 
	
	@NotNull(message = "Enabled flag is required")
	 Boolean enabled
		) {
}
