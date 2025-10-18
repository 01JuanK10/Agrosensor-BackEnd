package com.backend.agrosensor.agrosensorbackend.service.devices.impl;

import com.backend.agrosensor.agrosensorbackend.entity.impl.devices.Esp32;
import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.repository.devices.IEsp32Repository;
import com.backend.agrosensor.agrosensorbackend.repository.users.IClientRepository;
import com.backend.agrosensor.agrosensorbackend.service.devices.base.IDeviceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Esp32Service implements IDeviceService<Esp32> {

    private final IEsp32Repository esp32Repository;
    private final IClientRepository clientRepository;

    public Esp32Service(IEsp32Repository esp32Repository, IClientRepository clientRepository) {
        this.esp32Repository = esp32Repository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Esp32 create(Esp32 device) {
        if (esp32Repository.existsById(device.getId())) {
            throw new RuntimeException("Device already exists");
        }

        Long clientId = device.getClient().getCc();
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        device.setClient(client);
        return esp32Repository.save(device);
    }


    @Override
    public Esp32 findById(String id) throws RuntimeException {
        return esp32Repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Device not found"));
    }

    @Override
    public List<Esp32> findAll() {
        return esp32Repository.findAll();
    }

    @Override
    public Esp32 update(Esp32 device) throws RuntimeException {
        if (esp32Repository.findById(device.getId()).isEmpty()) {
            throw new RuntimeException("Device not found");
        }
        return esp32Repository.save(device);
    }

    @Override
    public void delete(String id) throws RuntimeException {
        if (esp32Repository.findById(id).isEmpty()) {
            throw new RuntimeException("Device not found");
        }
        esp32Repository.deleteById(id);
    }
}
