package com.backend.agrosensor.agrosensorbackend.controller.users;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Admin;
import com.backend.agrosensor.agrosensorbackend.service.users.impl.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
