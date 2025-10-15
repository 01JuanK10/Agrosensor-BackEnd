package com.backend.agrosensor.agrosensorbackend.service.measurements.impl;

import com.backend.agrosensor.agrosensorbackend.entity.impl.measurements.SoilMeasurement;
import com.backend.agrosensor.agrosensorbackend.repository.measurements.ISoilMeasurementsRepository;
import com.backend.agrosensor.agrosensorbackend.service.measurements.base.IMeasurementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoilMeasurementService implements IMeasurementService<SoilMeasurement> {
    
    private final ISoilMeasurementsRepository soilMeasurementsRepository;
    
    public SoilMeasurementService(ISoilMeasurementsRepository soilMeasurementsRepository) {
        this.soilMeasurementsRepository = soilMeasurementsRepository;
    }
    
    @Override
    public SoilMeasurement create(SoilMeasurement measurement) throws RuntimeException {
        return soilMeasurementsRepository.save(measurement);
    }

    @Override
    public List<SoilMeasurement> findAll() {
        return soilMeasurementsRepository.findAll();
    }
}
