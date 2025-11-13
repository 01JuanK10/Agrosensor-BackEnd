package com.backend.agrosensor.agrosensorbackend.service.utilities;

import com.backend.agrosensor.agrosensorbackend.dto.MapPoint;
import com.backend.agrosensor.agrosensorbackend.entity.impl.devices.Esp32;
import com.backend.agrosensor.agrosensorbackend.entity.impl.measurements.SoilMeasurement;
import com.backend.agrosensor.agrosensorbackend.service.devices.base.IDeviceService;
import com.backend.agrosensor.agrosensorbackend.service.measurements.base.IMeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ErosionPointService {

    private final IDeviceService<Esp32> deviceService;
    private final IMeasurementService<SoilMeasurement> measurementService;


    public List<MapPoint> getErosionPointsByClient(Long cedula) {
        List<Esp32> devices = deviceService.findAll().stream()
                .filter(d -> d.getClient() != null && d.getClient().getCc().equals(cedula))
                .toList();

        List<SoilMeasurement> allMeasurements = measurementService.findAll();

        return devices.stream()
                .map(device -> {
                    Optional<SoilMeasurement> latestMeasurement = allMeasurements.stream()
                            .filter(m -> m.getDevice().getId().equals(device.getId()))
                            .max(Comparator.comparing(SoilMeasurement::getDateTime)); // asumiendo que tiene un campo 'date'

                    return latestMeasurement.map(m ->
                            new MapPoint(
                                    device.getLocation().getLatitude(),
                                    device.getLocation().getLongitude(),
                                    device.getLocation().getAddress(),
                                    m.getErosion()
                            )
                    ).orElse(null);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
