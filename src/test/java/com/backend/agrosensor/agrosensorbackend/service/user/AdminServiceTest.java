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

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Admin;
import com.backend.agrosensor.agrosensorbackend.repository.users.IAdminRepository;
import com.backend.agrosensor.agrosensorbackend.service.users.impl.AdminService;

@SpringBootTest
class AdminServiceTest {

    @Mock
    private IAdminRepository repo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private AdminService service;

    @Test
    void create_should_save_admin() {
        Admin a = new Admin();
        a.setCc(5L);

        when(repo.findByCc(5L)).thenReturn(Optional.empty());
        when(repo.save(a)).thenReturn(a);

        Admin result = service.create(a);

        assertNotNull(result);
        verify(repo).save(a);
    }

    @Test
    void findByCc_should_return_admin() {
        Admin a = new Admin();
        when(repo.findByCc(7L)).thenReturn(Optional.of(a));

        Admin result = service.findByCc(7L);

        assertEquals(a, result);
    }

    @Test
    void patch_should_update_selected_fields() {
        Admin existing = new Admin();
        existing.setCc(10L);

        when(repo.findByCc(10L)).thenReturn(Optional.of(existing));
        when(encoder.encode("newpass")).thenReturn("ENCODED");
        when(repo.save(any())).thenReturn(existing);

        Map<String, Object> updates = Map.of(
                "name", "Carlos",
                "password", "newpass"
        );

        Admin result = service.patch(10L, updates);

        assertEquals("Carlos", result.getName());
        assertEquals("ENCODED", result.getPassword());
    }
}
