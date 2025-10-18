package com.backend.agrosensor.agrosensorbackend.service.utilities;

import com.backend.agrosensor.agrosensorbackend.entity.impl.measurements.SoilMeasurement;
import org.springframework.stereotype.Service;

@Service
public class SoilAnalyzer {

    // Coeficientes ajustables según calibración o modelo entrenado
    private static final double B0 = 5.0;   // Intercepto base
    private static final double B1 = -0.04; // Humedad del suelo (soilMoisture)
    private static final double B2 = 0.08;  // Temperatura ambiente (environmentTemperature)
    private static final double B3 = -0.03; // Humedad ambiente (environmentMoisture)
    private static final double B4 = 0.05;  // Iluminancia (soilIluminance)

    public double calcularErosion(SoilMeasurement m) {
        // Modelo de regresión lineal múltiple
        double erosion = B0
                + (B1 * m.getSoilMoisture())
                + (B2 * m.getEnvironmentTemperature())
                + (B3 * m.getEnvironmentMoisture())
                + (B4 * m.getSoilIluminance());

        // Limita la erosión a un rango razonable
        return Math.max(0, erosion);
    }

    public String analyze(SoilMeasurement m) {
        double erosion = calcularErosion(m);

        StringBuilder status = new StringBuilder("Erosion index: " + String.format("%.2f", erosion) + ". ");

        if (erosion < 10)
            status.append("Low risk of soil erosion.");
        else if (erosion < 20)
            status.append("Moderate risk of soil erosion.");
        else
            status.append("High risk of soil erosion.");

        return status.toString();
    }
}
