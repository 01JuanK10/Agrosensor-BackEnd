package com.backend.agrosensor.agrosensorbackend.service.impl;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Admin;
import com.backend.agrosensor.agrosensorbackend.repository.users.IAdminRepository;
import com.backend.agrosensor.agrosensorbackend.service.base.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IUserService<Admin> {
    @Autowired
    private IAdminRepository adminRepository;

    @Override
    public Admin create(Admin user) throws RuntimeException {
        if (adminRepository.findById(user.getCc()).isPresent()) {
            throw new RuntimeException();
        }
        return adminRepository.save(user);
    }

    @Override
    public Admin findByCc(Long cc) throws RuntimeException {
        return adminRepository.findById(cc)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin update(Admin user) throws RuntimeException {
        if (adminRepository.findById(user.getCc()).isEmpty()) {
            throw new RuntimeException();
        }
        return adminRepository.save(user);
    }

    @Override
    public void delete(Admin user) throws RuntimeException {

    }
}
