package com.backend.agrosensor.agrosensorbackend.service.notification;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.agrosensor.agrosensorbackend.entity.impl.notifications.AppNotification;
import com.backend.agrosensor.agrosensorbackend.repository.notifications.IAppNotificationRepository;
import com.backend.agrosensor.agrosensorbackend.service.notifications.impl.AppNotificationService;

@SpringBootTest
class AppNotificationServiceTest {

    @Mock
    private IAppNotificationRepository repo;

    @InjectMocks
    private AppNotificationService service;

    @Test
    void create_should_save_notification() {
        AppNotification n = new AppNotification();
        when(repo.save(n)).thenReturn(n);

        AppNotification saved = service.create(n);

        assertNotNull(saved);
        verify(repo).save(n);
    }

    @Test
    void findById_should_return_notification() {
        AppNotification n = new AppNotification();
        when(repo.findById(1L)).thenReturn(Optional.of(n));

        AppNotification result = service.findById(1L);

        assertEquals(n, result);
    }

    @Test
    void update_should_save_changes() {
        AppNotification existing = new AppNotification();
        existing.setId(1L);
        existing.setMessage("Old");

        AppNotification updated = new AppNotification();
        updated.setId(1L);
        updated.setMessage("New");

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenReturn(updated);

        AppNotification result = service.update(updated);

        assertEquals("New", result.getMessage());
    }

    @Test
    void delete_should_remove_notification() {
        when(repo.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repo).deleteById(1L);
    }
}
