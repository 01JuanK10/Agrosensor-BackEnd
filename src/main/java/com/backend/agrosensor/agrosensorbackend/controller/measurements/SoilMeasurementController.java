package com.backend.agrosensor.agrosensorbackend.controller.measurements;

import com.backend.agrosensor.agrosensorbackend.entity.impl.measurements.SoilMeasurement;
import com.backend.agrosensor.agrosensorbackend.service.measurements.impl.SoilMeasurementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
