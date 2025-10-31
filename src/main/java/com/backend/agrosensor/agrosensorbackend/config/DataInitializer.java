package com.backend.agrosensor.agrosensorbackend.config;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Admin;
import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.repository.users.IAdminRepository;
import com.backend.agrosensor.agrosensorbackend.repository.users.IClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IAdminRepository adminRepository;
    private final IClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setCc(1000L);
            admin.setName("System");
            admin.setLastname("Administrator");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            adminRepository.save(admin);
        }

        if (clientRepository.count() == 0) {
            Client client = new Client();
            client.setCc((long)1234567890);
            client.setName("Camilo1");
            client.setLastname("Alzate1");
            client.setUsername("camilo.alzate");
            client.setPassword(passwordEncoder.encode("password123"));
            client.setRole("CLIENT");
            clientRepository.save(client);
        }
    }
}
