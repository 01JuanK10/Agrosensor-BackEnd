package com.backend.agrosensor.agrosensorbackend.service.measurement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.agrosensor.agrosensorbackend.entity.base.device.Device;
import com.backend.agrosensor.agrosensorbackend.entity.impl.measurements.SoilMeasurement;
import com.backend.agrosensor.agrosensorbackend.entity.impl.notifications.AppNotification;
import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.repository.measurements.ISoilMeasurementsRepository;
import com.backend.agrosensor.agrosensorbackend.service.measurements.impl.SoilMeasurementService;
import com.backend.agrosensor.agrosensorbackend.service.notifications.impl.AppNotificationService;
import com.backend.agrosensor.agrosensorbackend.service.users.impl.ClientService;
import com.backend.agrosensor.agrosensorbackend.service.utilities.SoilAnalyzer;

@SpringBootTest
class SoilMeasurementServiceTest {

    @Mock
    private ISoilMeasurementsRepository repo;
    @Mock
    private SoilAnalyzer analyzer;
    @Mock
    private AppNotificationService notificationService;
    @Mock
    private ClientService clientService;

    @InjectMocks
    private SoilMeasurementService service;

    @Test
    void create_should_save_measurement_and_send_notification_for_high_risk() {
        SoilMeasurement m = new SoilMeasurement();
        m.setSoilMoisture(5f);

        Device device = new Device() {};
        Client client = new Client();
        client.setCc(10L);
        device.setClient(client);

        m.setDevice(device);

        when(analyzer.calcularErosionIndex(m)).thenReturn(80.0);
        when(analyzer.analyze(m)).thenReturn("High erosion risk");
        when(clientService.findByCc(10L)).thenReturn(client);
        when(repo.save(any())).thenReturn(m);

        SoilMeasurement saved = service.create(m);

        assertNotNull(saved);
        verify(notificationService, times(1)).create(any(AppNotification.class));
        verify(repo).save(m);
    }

    @Test
    void findAll_should_return_all_measurements() {
        when(repo.findAll()).thenReturn(List.of(new SoilMeasurement()));

        List<SoilMeasurement> list = service.findAll();

        assertEquals(1, list.size());
        verify(repo).findAll();
    }
}
