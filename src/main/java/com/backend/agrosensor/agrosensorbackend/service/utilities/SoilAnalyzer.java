package com.backend.agrosensor.agrosensorbackend.service.utilities;

import com.backend.agrosensor.agrosensorbackend.entity.impl.measurements.SoilMeasurement;
import org.springframework.stereotype.Service;

@Service
public class SoilAnalyzer {

    // Pesos (sum approx 1.0)
    private static final double W0 = 0.05;       // base
    private static final double W_SOIL = 0.40;   // humedad del suelo (importante)
    private static final double W_TEMP = 0.20;   // temperatura ambiente
    private static final double W_AIR = 0.20;    // humedad ambiental
    private static final double W_ILLUM = 0.15;  // iluminancia del suelo

    private static final double LOW_MAX = 35.0;
    private static final double MODERATE_MAX = 60.0;

    private static final double TEMP_MIN = 10.0;
    private static final double TEMP_MAX = 40.0;

    private static class EnvData {
        final double soilMoisture;
        final double envTemp;
        final double envMoist;
        final double illum;

        EnvData(SoilMeasurement m) {
            this.soilMoisture = m.getSoilMoisture() == null ? 0.0 : m.getSoilMoisture();
            this.envTemp = m.getEnvironmentTemperature() == null ? TEMP_MIN : m.getEnvironmentTemperature();
            this.envMoist = m.getEnvironmentMoisture() == null ? 0.0 : m.getEnvironmentMoisture();
            this.illum = m.getSoilIluminance() == null ? 0.0 : m.getSoilIluminance();
        }
    }

    private double clamp01(double v) {
        if (Double.isNaN(v)) return 0.0;
        return Math.max(0.0, Math.min(1.0, v));
    }

    private double normTemp(double temp) {
        return clamp01((temp - TEMP_MIN) / (TEMP_MAX - TEMP_MIN));
    }

    // Calcula el índice de erosión en 0..100
    public double calcularErosionIndex(SoilMeasurement m) {
        EnvData d = new EnvData(m);

        double vSoil = clamp01(1.0 - d.soilMoisture / 100.0); // suelo seco => 1
        double vTemp = normTemp(d.envTemp);                  // temp alta => 1
        double vAir = clamp01(1.0 - d.envMoist / 100.0);     // aire seco => 1
        double vIllum = clamp01(d.illum / 100.0);            // mucha luz => 1

        double scoreNorm = W0 + (W_SOIL * vSoil) + (W_TEMP * vTemp)
                + (W_AIR * vAir) + (W_ILLUM * vIllum);

        double erosion = Math.max(0.0, Math.min(100.0, scoreNorm * 100.0));

        // Ajustes heurísticos
        if (d.soilMoisture < 10.0) erosion += 8.0;
        if (d.illum > 85.0) erosion += 5.0;
        if (d.envMoist < 15.0) erosion += 4.0;

        return Math.max(0.0, Math.min(100.0, erosion));
    }

    public String getRiskLevel(double erosionIndex) {
        if (erosionIndex < LOW_MAX) return "Low";
        if (erosionIndex < MODERATE_MAX) return "Moderate";
        return "High";
    }

    // Mensaje detallado combinado (índice + nivel + checks heurísticos)
    public String analyze(SoilMeasurement m) {
        EnvData d = new EnvData(m);
        String risk = getRiskLevel(m.getErosion());

        StringBuilder status = new StringBuilder();
        status.append(String.format("Erosion index: %.2f. %s risk of soil erosion. ", m.getErosion(), risk));

        // Humedad suelo
        if (d.soilMoisture < 10) status.append("Soil extremely dry. ");
        else if (d.soilMoisture < 40) status.append("Soil dryness may increase erosion. ");
        else if (d.soilMoisture > 80) status.append("Soil well moist, erosion less likely. ");

        // Temperatura
        if (d.envTemp < 15) status.append("Low temperature (reduced evaporation). ");
        else if (d.envTemp > 32) status.append("High temperature may increase evaporation. ");

        // Humedad ambiente
        if (d.envMoist < 20) status.append("Air very dry. ");
        else if (d.envMoist > 70) status.append("Air humidity high. ");

        // Iluminancia
        if (d.illum < 20) status.append("Low light (possible dense cover). ");
        else if (d.illum > 80) status.append("High light (possible low vegetation cover). ");

        return status.toString();
    }
}
