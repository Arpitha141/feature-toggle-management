package com.example.featuretoggle.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.featuretoggle.dto.Environment;
import com.example.featuretoggle.dto.FeatureCheckResponse;
import com.example.featuretoggle.dto.FeatureCreateRequest;
import com.example.featuretoggle.dto.FeatureToggleRequest;
import com.example.featuretoggle.dto.FeatureToggleResponse;
import com.example.featuretoggle.service.FeatureToggleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/features")
@Validated
@Tag(name = "Feature Toggle API", description = "Endpoints for managing feature toggles")
public class FeatureToggleController {

	private final FeatureToggleService service;

    public FeatureToggleController(FeatureToggleService service) {
        this.service = service;
    }
    
    @PostMapping
    @Operation(summary = "Create a new Feature Toggle")
    public ResponseEntity<FeatureToggleResponse> createFeature(@Valid @RequestBody FeatureCreateRequest request) {
        FeatureToggleResponse response = service.createFeature(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/check")
    @Operation(summary = "Check Feature Status")
    public ResponseEntity<FeatureCheckResponse> checkFeature(
            @RequestParam String featureName,
            @RequestParam Environment environment) {
    	FeatureCheckResponse response = service.checkFeature(featureName, environment);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}/toggle")
    @Operation(summary = "Toggle a Feature on or off")
    public ResponseEntity<FeatureToggleResponse> toggleFeature(
            @PathVariable Long id,
            @Valid @RequestBody FeatureToggleRequest request) {
        FeatureToggleResponse response = service.toggleFeature(id, request);
        return ResponseEntity.ok(response);
    }
}
    
