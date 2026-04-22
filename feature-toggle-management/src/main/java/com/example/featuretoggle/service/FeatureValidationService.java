package com.example.featuretoggle.service;

import org.springframework.stereotype.Service;

@Service
public class FeatureValidationService {

	public void validateFeatureName(String featureName) {
        if (featureName == null || featureName.isBlank()) {
            throw new IllegalArgumentException("Feature name cannot be empty");
        }
        if (featureName.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Feature name cannot contain uppercase letters");
        }
        if (featureName.contains(" ")) {
            throw new IllegalArgumentException("Feature name cannot contain spaces");
        }
        if (featureName.startsWith("-") || featureName.endsWith("-")) {
            throw new IllegalArgumentException("Feature name cannot start or end with a hyphen ('-')");
        }
    }
}
