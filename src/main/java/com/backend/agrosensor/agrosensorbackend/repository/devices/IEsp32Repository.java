package com.backend.agrosensor.agrosensorbackend.repository.devices;

import com.backend.agrosensor.agrosensorbackend.entity.impl.devices.Esp32;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEsp32Repository extends JpaRepository<Esp32, String> {
}
