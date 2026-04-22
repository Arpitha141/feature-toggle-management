package com.example.featuretoggle.entity;

import com.example.featuretoggle.dto.Environment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
		name = "feature_toggles", 
		uniqueConstraints = {@UniqueConstraint(columnNames = {"feature_name", "environment"})}
		)
public class FeatureToggle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "feature_name", nullable = false)
	private String featureName;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private Environment environment;
	
	@Column(nullable = false)
	private boolean enabled;
	
	
	public FeatureToggle() {}

	
	public FeatureToggle(String featureName, Environment environment, boolean enabled) {
        this.featureName = featureName;
        this.environment = environment;
        this.enabled = enabled;
    }
	
	public Long getId() { return id; }
    public String getFeatureName() { return featureName; }
    public Environment getEnvironment() { return environment; }
    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
