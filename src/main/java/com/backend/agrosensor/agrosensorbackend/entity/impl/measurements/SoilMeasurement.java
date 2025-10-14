package com.backend.agrosensor.agrosensorbackend.entity.impl.measurements;

import com.backend.agrosensor.agrosensorbackend.entity.base.Measurement;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "soil_measurement")
public class SoilMeasurement extends Measurement {
    private Float soilIluminance;
    private Float soilMoisture;
}
