package com.backend.agrosensor.agrosensorbackend.service.utilities;

import com.backend.agrosensor.agrosensorbackend.entity.impl.measurements.SoilMeasurement;
import org.springframework.stereotype.Service;

@Service
public class SoilAnalyzer {

    public String analyze(SoilMeasurement m) {
        StringBuilder status = new StringBuilder();

        if (m.getSoilMoisture() < 40)
            status.append("Soil too dry. ");
        else if (m.getSoilMoisture() > 70)
            status.append("Soil too wet. ");
        else
            status.append("Soil moisture normal. ");

        if (m.getEnvironmentTemperature() < 20)
            status.append("Temperature too low. ");
        else if (m.getEnvironmentTemperature() > 30)
            status.append("Temperature too high. ");
        else
            status.append("Temperature normal. ");

        if (m.getEnvironmentMoisture() < 40)
            status.append("Air too dry. ");
        else if (m.getEnvironmentMoisture() > 60)
            status.append("Air too humid. ");
        else
            status.append("Air humidity normal. ");

        if (m.getSoilIluminance() < 40)
            status.append("Low light. ");
        else if (m.getSoilIluminance() > 80)
            status.append("Too much light. ");
        else
            status.append("Light level normal. ");

        return status.toString();
    }
}
