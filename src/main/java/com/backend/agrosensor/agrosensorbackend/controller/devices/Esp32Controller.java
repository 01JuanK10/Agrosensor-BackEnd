package com.backend.agrosensor.agrosensorbackend.controller.devices;

import com.backend.agrosensor.agrosensorbackend.entity.impl.devices.Esp32;
import com.backend.agrosensor.agrosensorbackend.service.devices.impl.Esp32Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices/esp32")
public class Esp32Controller {

    private final Esp32Service esp32Service;

    public Esp32Controller(Esp32Service esp32Service) {
        this.esp32Service = esp32Service;
    }

    @PostMapping
    public ResponseEntity<Esp32> create(@RequestBody Esp32 device) {
        return ResponseEntity.ok(esp32Service.create(device));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Esp32> findById(@PathVariable String id) {
        return ResponseEntity.ok(esp32Service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Esp32>> findAll() {
        return ResponseEntity.ok(esp32Service.findAll());
    }

    @PutMapping
    public ResponseEntity<Esp32> update(@RequestBody Esp32 device) {
        return ResponseEntity.ok(esp32Service.update(device));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        esp32Service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
