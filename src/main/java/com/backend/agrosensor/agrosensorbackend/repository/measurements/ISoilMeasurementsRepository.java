package com.backend.agrosensor.agrosensorbackend.repository.measurements;

import com.backend.agrosensor.agrosensorbackend.entity.impl.measurements.SoilMeasurements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISoilMeasurementsRepository extends JpaRepository<SoilMeasurements,Integer> {
}
