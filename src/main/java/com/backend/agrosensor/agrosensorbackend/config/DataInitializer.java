package com.backend.agrosensor.agrosensorbackend.config;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.backend.agrosensor.agrosensorbackend.entity.base.device.Location;
import com.backend.agrosensor.agrosensorbackend.entity.impl.devices.Esp32;
import com.backend.agrosensor.agrosensorbackend.entity.impl.measurements.SoilMeasurement;
import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Admin;
import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.repository.devices.IEsp32Repository;
import com.backend.agrosensor.agrosensorbackend.repository.users.IAdminRepository;
import com.backend.agrosensor.agrosensorbackend.repository.users.IClientRepository;
import com.backend.agrosensor.agrosensorbackend.service.measurements.impl.SoilMeasurementService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IAdminRepository adminRepository;
    private final IClientRepository clientRepository;
    private final IEsp32Repository deviceRepository;
    private final PasswordEncoder passwordEncoder;
    private final SoilMeasurementService soilMeasurementService;

    Random random = new Random();

    @Override
    public void run(String... args) {

        // ========================
        //  1. Crear ADMIN
        // ========================
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setCc(1000L);
            admin.setName("System");
            admin.setLastname("Administrator");
            admin.setEmail("example@email.com");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            adminRepository.save(admin);

            System.out.println("✔ Admin creado");
        }

        // ========================
        //  2. Crear CLIENTE
        // ========================
        Client client = clientRepository.findByCc(1234567890L).orElse(null);

        if (client == null) {
            client = new Client();
            client.setCc(1234567890L);
            client.setName("Camilo1");
            client.setLastname("Alzate1");
            client.setEmail("camiloalzatebedoya15@gmail.com");
            client.setUsername("camilo.alzate");
            client.setPassword(passwordEncoder.encode("password123"));
            client.setRole("CLIENT");
            clientRepository.save(client);

            System.out.println("✔ Cliente creado");
        }

        // ========================
        //  3. Crear DISPOSITIVOS ESP32
        // ========================
        Esp32 device = deviceRepository.findById("AA:BB:CC:DD:EE:FF").orElse(null);

        if (device == null) {
            device = new Esp32();
            device.setId("AA:BB:CC:DD:EE:FF");
            device.setType("esp32");
            device.setActive(true);
            device.setLocation(new Location());
            device.getLocation().setLatitude(6.25184f);
            device.getLocation().setLongitude(-75.56359f);
            device.getLocation().setAddress("Medellin, Colombia");
            device.setClient(client);

            deviceRepository.save(device);

            System.out.println("✔ Dispositivo ESP32 creado");
        }

        Esp32 device2 = deviceRepository.findById("11:22:33:44:55:66").orElse(null);

        if (device2 == null) {
            device2 = new Esp32();
            device2.setId("11:22:33:44:55:66");
            device2.setType("esp32");
            device2.setActive(true);

            device2.setLocation(new Location());

            // Coordenadas cercanas (variación leve)
            device2.getLocation().setLatitude(6.25184f + randomOffset(0.001f));
            device2.getLocation().setLongitude(-75.56359f + randomOffset(0.001f));

            device2.getLocation().setAddress("Medellín, Colombia");
            device2.setClient(client);

            deviceRepository.save(device2);

            System.out.println("✔ Segundo dispositivo ESP32 creado");
        }

        // ========================
        //  4. Crear MEDICIONES
        // ========================
        if (soilMeasurementService.findAll().isEmpty()) {

            for (int i = 0; i < 5; i++) {

                SoilMeasurement m = new SoilMeasurement();
                m.setSoilMoisture(randomValue(0, 100));
                m.setSoilIluminance(randomValue(0, 100));
                m.setEnvironmentTemperature(randomValue(15, 35));
                m.setEnvironmentMoisture(randomValue(30, 90));
                m.setErosion(0f);
                m.setDateTime(LocalDateTime.now().minusMinutes(i * 10));
                m.setDevice(device);

                SoilMeasurement m2 = new SoilMeasurement();
                m2.setSoilMoisture(randomValue(0, 100));
                m2.setSoilIluminance(randomValue(0, 100));
                m2.setEnvironmentTemperature(randomValue(15, 35));
                m2.setEnvironmentMoisture(randomValue(30, 90));
                m2.setErosion(0f);
                m2.setDateTime(LocalDateTime.now().minusMinutes(i * 8));
                m2.setDevice(device2);

                soilMeasurementService.create(m);
                soilMeasurementService.create(m2);

            }

            System.out.println("✔ 10 mediciones creadas");
        }


    }

    private float randomValue(float min, float max) {
        return (float)(Math.round((min + random.nextFloat() * (max - min)) * 10.0) / 10.0);
    }

    private float randomOffset(float factor) {
        return (float) ((Math.random() - 0.5) * factor); // variación entre -factor/2 y +factor/2
    }

}
