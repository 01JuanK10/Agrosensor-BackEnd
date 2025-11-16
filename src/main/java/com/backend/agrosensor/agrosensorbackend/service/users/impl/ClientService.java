package com.backend.agrosensor.agrosensorbackend.service.users.impl;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.repository.users.IClientRepository;
import com.backend.agrosensor.agrosensorbackend.service.users.base.IUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClientService implements IUserService<Client> {

    private final IClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Client create(Client user) throws RuntimeException {
        if (clientRepository.findByCc(user.getCc()).isPresent()) {
            throw new RuntimeException("Client already exists");
        }
        return clientRepository.save(user);
    }

    @Override
    public Client findByCc(Long cc) throws RuntimeException {
        return clientRepository.findByCc(cc)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client update(Client user) throws RuntimeException {
        if (clientRepository.findByCc(user.getCc()).isEmpty()) {
            throw new RuntimeException("Client not found");
        }
        return clientRepository.save(user);
    }

    @Override
    public void delete(Long cc) throws RuntimeException {
        if (clientRepository.findByCc(cc).isEmpty()) {
            throw new RuntimeException("Client not found");
        }
        clientRepository.deleteById(cc);
    }

    @Override
    public Client patch(Long cc, Map<String, Object> updates) {
        Client client = clientRepository.findByCc(cc)
            .orElseThrow(() -> new RuntimeException("Client not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> client.setName((String) value);
                case "lastname" -> client.setLastname((String) value);
                case "email" -> client.setEmail((String) value);
                case "username" -> client.setUsername((String) value);
                case "password" -> client.setPassword(passwordEncoder.encode((String) value));
                default -> throw new RuntimeException("Campo no permitido: " + key);
            }
        });

        return clientRepository.save(client);
    }

}
