package com.backend.agrosensor.agrosensorbackend.service.users.impl;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Admin;
import com.backend.agrosensor.agrosensorbackend.repository.users.IAdminRepository;
import com.backend.agrosensor.agrosensorbackend.service.users.base.IUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AdminService implements IUserService<Admin> {

    private final IAdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Admin create(Admin user) throws RuntimeException {
        if (adminRepository.findByCc(user.getCc()).isPresent()) {
            throw new RuntimeException();
        }
        return adminRepository.save(user);
    }

    @Override
    public Admin findByCc(Long cc) throws RuntimeException {
        return adminRepository.findByCc(cc)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin update(Admin user) throws RuntimeException {
        if (adminRepository.findByCc(user.getCc()).isEmpty()) {
            throw new RuntimeException();
        }
        return adminRepository.save(user);
    }

    @Override
    public void delete(Long cc) throws RuntimeException {
        if (adminRepository.findByCc(cc).isEmpty()) {
            throw new RuntimeException();
        }
        adminRepository.deleteById(cc);
    }

    @Override
    public Admin patch(Long cc, Map<String, Object> updates) {
        Admin admin = adminRepository.findByCc(cc)
            .orElseThrow(() -> new RuntimeException("Admin not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> admin.setName((String) value);
                case "lastname" -> admin.setLastname((String) value);
                case "email" -> admin.setEmail((String) value);
                case "username" -> admin.setUsername((String) value);
                case "password" -> admin.setPassword(passwordEncoder.encode((String) value));
                default -> throw new RuntimeException("Campo no permitido: " + key);
            }
        });

        return adminRepository.save(admin);
    }

}
