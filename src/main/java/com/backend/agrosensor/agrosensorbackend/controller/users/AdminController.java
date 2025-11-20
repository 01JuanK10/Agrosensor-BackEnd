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

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Admin;
import com.backend.agrosensor.agrosensorbackend.service.users.impl.AdminService;

@RestController
@RequestMapping("/api/users/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/{cc}")
    public ResponseEntity<Admin> findByCc(@PathVariable Long cc) {
        return ResponseEntity.ok(adminService.findByCc(cc));
    }

    @GetMapping
    public ResponseEntity<List<Admin>> findAll() {
        return ResponseEntity.ok(adminService.findAll());
    }

    @PutMapping
    public ResponseEntity<Admin> update(@RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.update(admin));
    }

    @DeleteMapping("/{cc}")
    public ResponseEntity<Void> delete(@PathVariable Long cc) {
        adminService.delete(cc);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(adminService.patch(id, updates));
    }
}
