package com.backend.agrosensor.agrosensorbackend.service.notifications.impl;

import com.backend.agrosensor.agrosensorbackend.entity.impl.notifications.AppNotification;
import com.backend.agrosensor.agrosensorbackend.repository.notifications.IAppNotificationRepository;
import com.backend.agrosensor.agrosensorbackend.service.notifications.base.INotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppNotificationService implements INotificationService<AppNotification> {

    private final IAppNotificationRepository notificationRepository;

    public AppNotificationService(IAppNotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public AppNotification create(AppNotification notification) {
        if (notification == null)
            throw new RuntimeException("Notification cannot be null");
        return notificationRepository.save(notification);
    }

    @Override
    public AppNotification findById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + id));
    }

    @Override
    public List<AppNotification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public AppNotification update(AppNotification notification) {
        if (notification.getId() == null)
            throw new RuntimeException("Notification ID cannot be null for update");

        AppNotification existing = findById(notification.getId());
        existing.setMessage(notification.getMessage());
        existing.setReadState(notification.getReadState());
        return notificationRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!notificationRepository.existsById(id))
            throw new RuntimeException("Notification not found with ID: " + id);

        notificationRepository.deleteById(id);
    }
}
