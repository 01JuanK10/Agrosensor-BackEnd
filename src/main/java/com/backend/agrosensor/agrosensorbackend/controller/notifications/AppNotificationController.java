package com.backend.agrosensor.agrosensorbackend.controller.notifications;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.agrosensor.agrosensorbackend.entity.base.Notification;
import com.backend.agrosensor.agrosensorbackend.entity.impl.notifications.AppNotification;
import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.service.notifications.impl.AppNotificationService;
import com.backend.agrosensor.agrosensorbackend.service.users.base.IUserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/notifications")
public class AppNotificationController {

    private final AppNotificationService appNotificationService;
    private final IUserService<Client> clientService;


    @PostMapping
    public ResponseEntity<AppNotification> create(@RequestBody AppNotification notification) {
        return ResponseEntity.ok(appNotificationService.create(notification));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppNotification> findById(@PathVariable Long id) {
        return ResponseEntity.ok(appNotificationService.findById(id));
    }

    @GetMapping("/{cc}")
    public ResponseEntity<List<? extends Notification>> findNotificationByClientCc(@PathVariable Long cc) {
        return ResponseEntity.ok(clientService.findByCc(cc).getNotifications());
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
