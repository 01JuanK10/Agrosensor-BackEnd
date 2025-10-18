package com.backend.agrosensor.agrosensorbackend.entity.impl.devices;

import com.backend.agrosensor.agrosensorbackend.entity.base.device.Device;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "esp32")
public class Esp32 extends Device {

    public Esp32() {
        super.setType("esp32");
    }
}
