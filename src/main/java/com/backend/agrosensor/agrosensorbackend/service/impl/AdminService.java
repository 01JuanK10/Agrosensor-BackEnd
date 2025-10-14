package com.backend.agrosensor.agrosensorbackend.service.impl;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Admin;
import com.backend.agrosensor.agrosensorbackend.service.base.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IUserService<Admin> {

    @Override
    public Admin create(Admin user) {
        return null;
    }

    @Override
    public Admin findByCc(Long cc) {
        return null;
    }

    @Override
    public List<Admin> findAll() {
        return List.of();
    }

    @Override
    public Admin update(Admin user) {
        return null;
    }

    @Override
    public void delete(Admin user) {

    }
}
