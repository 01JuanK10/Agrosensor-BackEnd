package com.backend.agrosensor.agrosensorbackend.service.impl;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.service.base.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IUserService<Client> {

    @Override
    public Client create(Client user) {
        return null;
    }

    @Override
    public Client findByCc(Long cc) {
        return null;
    }

    @Override
    public List<Client> findAll() {
        return List.of();
    }

    @Override
    public Client update(Client user) {
        return null;
    }

    @Override
    public void delete(Client user) {

    }
}
