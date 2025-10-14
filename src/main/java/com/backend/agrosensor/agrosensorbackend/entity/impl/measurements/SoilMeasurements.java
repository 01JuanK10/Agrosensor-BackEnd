package com.backend.agrosensor.agrosensorbackend.entity.impl.measurements;

import com.backend.agrosensor.agrosensorbackend.entity.base.Measurement;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "soil_measurement")
public class SoilMeasurements extends Measurement {
    private Float soilIluminance;
    private Float soilMoisture;
}
