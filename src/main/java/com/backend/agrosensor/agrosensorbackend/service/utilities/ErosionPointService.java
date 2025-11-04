package com.backend.agrosensor.agrosensorbackend.service.utilities;

import com.backend.agrosensor.agrosensorbackend.dto.MapPoint;
import com.backend.agrosensor.agrosensorbackend.entity.impl.devices.Esp32;
import com.backend.agrosensor.agrosensorbackend.entity.impl.measurements.SoilMeasurement;
import com.backend.agrosensor.agrosensorbackend.service.devices.base.IDeviceService;
import com.backend.agrosensor.agrosensorbackend.service.measurements.base.IMeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ErosionPointService {
    private IDeviceService<Esp32> deviceService;
    private IMeasurementService<SoilMeasurement> measurementService;

    public MapPoint createErosionPoints(){
        return null;
    }
}
