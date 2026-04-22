package com.example.featuretoggle.dto;

import jakarta.validation.constraints.NotNull;

public record FeatureToggleRequest(
		@NotNull(message = "Enabled flag is required") 
	    Boolean enabled
	    ) {

}
