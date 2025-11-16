package com.backend.agrosensor.agrosensorbackend.controller.users;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.service.users.impl.ClientService;

@RestController
@RequestMapping("/api/users/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{cc}")
    public ResponseEntity<Client> findByCc(@PathVariable Long cc) {
        return ResponseEntity.ok(clientService.findByCc(cc));
    }

    @GetMapping
    public ResponseEntity<List<Client>> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @PutMapping
    public ResponseEntity<Client> update(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.update(client));
    }

    @DeleteMapping("/{cc}")
    public ResponseEntity<Void> delete(@PathVariable Long cc) {
        clientService.delete(cc);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(clientService.patch(id, updates));
    }

}
