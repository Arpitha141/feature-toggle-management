package com.example.featuretoggle.service;

import com.example.featuretoggle.dto.Environment;
import com.example.featuretoggle.dto.FeatureCheckResponse;
import com.example.featuretoggle.dto.FeatureCreateRequest;
import com.example.featuretoggle.dto.FeatureToggleRequest;
import com.example.featuretoggle.dto.FeatureToggleResponse;

public interface FeatureToggleService {
	
	FeatureToggleResponse createFeature(FeatureCreateRequest request);
	
	public FeatureCheckResponse checkFeature(String featureName, Environment environment);
	
	FeatureToggleResponse toggleFeature(Long id, FeatureToggleRequest request);

}
