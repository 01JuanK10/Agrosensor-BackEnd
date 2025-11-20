package com.backend.agrosensor.agrosensorbackend.service.user;

import java.util.Map;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.repository.users.IClientRepository;
import com.backend.agrosensor.agrosensorbackend.service.users.impl.ClientService;

@SpringBootTest
class ClientServiceTest {

    @Mock
    private IClientRepository repo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private ClientService service;

    @Test
    void create_should_save_client() {
        Client c = new Client();
        c.setCc(3L);

        when(repo.findByCc(3L)).thenReturn(Optional.empty());
        when(repo.save(c)).thenReturn(c);

        Client result = service.create(c);

        assertNotNull(result);
        verify(repo).save(c);
    }

    @Test
    void patch_should_apply_changes() {
        Client existing = new Client();
        existing.setCc(9L);

        when(repo.findByCc(9L)).thenReturn(Optional.of(existing));
        when(encoder.encode("pass")).thenReturn("ENCODED");
        when(repo.save(any())).thenReturn(existing);

        Map<String, Object> updates = Map.of(
                "lastname", "Perez",
                "password", "pass"
        );

        Client result = service.patch(9L, updates);

        assertEquals("Perez", result.getLastname());
        assertEquals("ENCODED", result.getPassword());
    }
}
