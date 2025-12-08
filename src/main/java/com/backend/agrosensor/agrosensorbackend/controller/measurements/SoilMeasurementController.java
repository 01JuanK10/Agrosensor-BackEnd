package com.backend.agrosensor.agrosensorbackend.controller.measurements;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.agrosensor.agrosensorbackend.entity.impl.measurements.SoilMeasurement;
import com.backend.agrosensor.agrosensorbackend.service.measurements.impl.SoilMeasurementService;

@RestController
@RequestMapping("/api/measurements/soil")
public class SoilMeasurementController {

    private final SoilMeasurementService soilMeasurementService;

    public SoilMeasurementController(SoilMeasurementService soilMeasurementService) {
        this.soilMeasurementService = soilMeasurementService;
    }

    @PostMapping
    public ResponseEntity<SoilMeasurement> create(@RequestBody SoilMeasurement measurement) {
        return ResponseEntity.ok(soilMeasurementService.create(measurement));
    }

    @GetMapping
    public ResponseEntity<List<SoilMeasurement>> findAll() {
        return ResponseEntity.ok(soilMeasurementService.findAll());
    }

    @GetMapping("/{cc}")
    public ResponseEntity<List<SoilMeasurement>> findAllByCc(@PathVariable Long cc) {
        return ResponseEntity.ok(soilMeasurementService.findAllByCc(cc));
    }
}
