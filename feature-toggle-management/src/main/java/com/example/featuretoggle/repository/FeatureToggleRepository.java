package com.example.featuretoggle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.featuretoggle.dto.Environment;
import com.example.featuretoggle.entity.FeatureToggle;

public interface FeatureToggleRepository extends JpaRepository<FeatureToggle, Long> {

	Optional<FeatureToggle>	findByFeatureNameAndEnvironment(String featureName , Environment environment);
	
	boolean existsByFeatureNameAndEnvironment(String featureName, Environment environment);
	
}
