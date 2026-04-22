package com.example.featuretoggle.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.featuretoggle.dto.Environment;
import com.example.featuretoggle.dto.FeatureCheckResponse;
import com.example.featuretoggle.dto.FeatureCreateRequest;
import com.example.featuretoggle.dto.FeatureToggleRequest;
import com.example.featuretoggle.dto.FeatureToggleResponse;
import com.example.featuretoggle.entity.FeatureToggle;
import com.example.featuretoggle.repository.FeatureToggleRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeatureToggleServiceImpl implements FeatureToggleService {


    private final FeatureToggleRepository repository;
    private final FeatureValidationService validationService;
    

    public FeatureToggleServiceImpl(
            FeatureToggleRepository repository,
            FeatureValidationService validationService) {
        this.repository = repository;
        this.validationService = validationService;
    }


	@Override
	@Transactional
	public FeatureToggleResponse createFeature(FeatureCreateRequest request) {
		validationService.validateFeatureName(request.featureName());
		
		if (repository.existsByFeatureNameAndEnvironment(request.featureName(), request.environment())) {
            throw new DataIntegrityViolationException(
                String.format("Feature '%s' already exists in environment '%s'", 
                              request.featureName(), request.environment())
            );
        }
		
		FeatureToggle featureToggle = new FeatureToggle(
                request.featureName(),
                request.environment(),
                request.enabled()
        );
		
		FeatureToggle savedEntity = repository.save(featureToggle);
		return FeatureToggleResponse.fromEntity(savedEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public FeatureCheckResponse checkFeature(String featureName, Environment environment) {
		FeatureToggle featureToggle = repository.findByFeatureNameAndEnvironment(featureName, environment)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Feature '%s' not found for environment '%s'", featureName, environment)
                ));
		return new FeatureCheckResponse(featureToggle.isEnabled());
	}

	@Override
	@Transactional
	public FeatureToggleResponse toggleFeature(Long id, FeatureToggleRequest request) {

		FeatureToggle featureToggle = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feature not found with ID: " + id));

		// Idempotent operation: simply set it to the requested state
        featureToggle.setEnabled(request.enabled());
        FeatureToggle updatedEntity = repository.save(featureToggle);
        return new FeatureToggleResponse(
        		updatedEntity.getId(),
        		updatedEntity.getFeatureName(),
        		updatedEntity.getEnvironment(),
        		updatedEntity.isEnabled()
				);
	}

}
