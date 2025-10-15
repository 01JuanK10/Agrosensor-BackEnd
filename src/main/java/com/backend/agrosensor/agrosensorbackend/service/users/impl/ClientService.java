package com.backend.agrosensor.agrosensorbackend.service.users.impl;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.repository.users.IClientRepository;
import com.backend.agrosensor.agrosensorbackend.service.users.base.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IUserService<Client> {

    private final IClientRepository clientRepository;

    public ClientService(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client create(Client user) throws RuntimeException {
        if (clientRepository.findById(user.getCc()).isPresent()) {
            throw new RuntimeException("Client already exists");
        }
        return clientRepository.save(user);
    }

    @Override
    public Client findByCc(Long cc) throws RuntimeException {
        return clientRepository.findById(cc)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client update(Client user) throws RuntimeException {
        if (clientRepository.findById(user.getCc()).isEmpty()) {
            throw new RuntimeException("Client not found");
        }
        return clientRepository.save(user);
    }

    @Override
    public void delete(Long cc) throws RuntimeException {
        if (clientRepository.findById(cc).isEmpty()) {
            throw new RuntimeException("Client not found");
        }
        clientRepository.deleteById(cc);
    }
}
