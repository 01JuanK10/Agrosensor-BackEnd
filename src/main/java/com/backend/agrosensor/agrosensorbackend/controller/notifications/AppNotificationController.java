package com.backend.agrosensor.agrosensorbackend.controller.notifications;

import com.backend.agrosensor.agrosensorbackend.entity.impl.notifications.AppNotification;
import com.backend.agrosensor.agrosensorbackend.service.notifications.impl.AppNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class AppNotificationController {

    private final AppNotificationService appNotificationService;

    public AppNotificationController(AppNotificationService appNotificationService) {
        this.appNotificationService = appNotificationService;
    }

    @PostMapping
    public ResponseEntity<AppNotification> create(@RequestBody AppNotification notification) {
        return ResponseEntity.ok(appNotificationService.create(notification));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppNotification> findById(@PathVariable Long id) {
        return ResponseEntity.ok(appNotificationService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AppNotification>> findAll() {
        return ResponseEntity.ok(appNotificationService.findAll());
    }

    @PutMapping
    public ResponseEntity<AppNotification> update(@RequestBody AppNotification notification) {
        return ResponseEntity.ok(appNotificationService.update(notification));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appNotificationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
