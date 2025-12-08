package com.backend.agrosensor.agrosensorbackend.repository.measurements;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.backend.agrosensor.agrosensorbackend.entity.impl.measurements.SoilMeasurement;

@Repository
public interface ISoilMeasurementsRepository extends JpaRepository<SoilMeasurement,Integer> {
    @Query("SELECT m FROM SoilMeasurement m WHERE m.device.client.cc = :cc")
    List<SoilMeasurement> findAllByClientCc(Long cc);

}
