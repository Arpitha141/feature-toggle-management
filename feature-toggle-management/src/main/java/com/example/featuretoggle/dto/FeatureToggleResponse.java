package com.example.featuretoggle.dto;

import com.example.featuretoggle.entity.FeatureToggle;

public record FeatureToggleResponse(
		Long id,
		String featureName, 
		Environment environment, 
		boolean enabled 
		) {
	public static FeatureToggleResponse fromEntity(FeatureToggle entity) {
        return new FeatureToggleResponse(
            entity.getId(), 
            entity.getFeatureName(), 
            entity.getEnvironment(), 
            entity.isEnabled()
        );
    }

}
