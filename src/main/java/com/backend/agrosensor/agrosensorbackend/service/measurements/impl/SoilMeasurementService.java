package com.backend.agrosensor.agrosensorbackend.service.measurements.impl;

import com.backend.agrosensor.agrosensorbackend.entity.impl.measurements.SoilMeasurement;
import com.backend.agrosensor.agrosensorbackend.entity.impl.notifications.AppNotification;
import com.backend.agrosensor.agrosensorbackend.repository.measurements.ISoilMeasurementsRepository;
import com.backend.agrosensor.agrosensorbackend.service.measurements.base.IMeasurementService;
import com.backend.agrosensor.agrosensorbackend.service.notifications.impl.AppNotificationService;
import com.backend.agrosensor.agrosensorbackend.service.users.impl.ClientService;
import com.backend.agrosensor.agrosensorbackend.service.utilities.SoilAnalyzer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoilMeasurementService implements IMeasurementService<SoilMeasurement> {
    
    private final ISoilMeasurementsRepository soilMeasurementsRepository;
    private final SoilAnalyzer soilAnalyzer;
    private final AppNotificationService appNotificationService;
    private final ClientService  clientService;

    public SoilMeasurementService(ISoilMeasurementsRepository soilMeasurementsRepository, SoilAnalyzer soilAnalyzer, AppNotificationService appNotificationService, ClientService  clientService) {
        this.soilMeasurementsRepository = soilMeasurementsRepository;
        this.soilAnalyzer = soilAnalyzer;
        this.appNotificationService = appNotificationService;
        this.clientService = clientService;
    }
    
    @Override
    public SoilMeasurement create(SoilMeasurement measurement) throws RuntimeException {
        measurement.setErosion((float) soilAnalyzer.calcularErosionIndex(measurement));
        String conditionReport = soilAnalyzer.analyze(measurement);
        if (conditionReport.contains("Moderate") || conditionReport.contains("High")) {
            AppNotification notification = new AppNotification();
            notification.setMessage("Alert: " + conditionReport);
            notification.setClient(
                    clientService.findByCc(
                            measurement.getDevice().getClient().getCc()
                    )
            );
            appNotificationService.create(notification);
        }
        return soilMeasurementsRepository.save(measurement);
    }

    @Override
    public List<SoilMeasurement> findAll() {
        return soilMeasurementsRepository.findAll();
    }
}
