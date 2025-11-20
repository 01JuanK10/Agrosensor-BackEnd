package com.backend.agrosensor.agrosensorbackend.service.device;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.agrosensor.agrosensorbackend.entity.impl.devices.Esp32;
import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.repository.devices.IEsp32Repository;
import com.backend.agrosensor.agrosensorbackend.repository.users.IClientRepository;
import com.backend.agrosensor.agrosensorbackend.service.devices.impl.Esp32Service;

@SpringBootTest
class Esp32ServiceTest {

    @Mock
    private IEsp32Repository repository;

    @Mock
    private IClientRepository clientRepository; // <- agregamos este mock

    @InjectMocks
    private Esp32Service service;

    @Test
    void findById_should_return_device() {
        Esp32 esp = new Esp32();
        esp.setId("abc");

        when(repository.findById("abc")).thenReturn(Optional.of(esp));

        Esp32 result = service.findById("abc");

        assertThat(result).isNotNull();
        verify(repository).findById("abc");
    }

    @Test
    void save_should_store_device() {
        Esp32 esp = new Esp32();
        Client client = new Client();
        client.setCc(100L);
        esp.setId("x1");
        esp.setClient(client);

        when(clientRepository.findByCc(100L)).thenReturn(Optional.of(client));
        when(repository.save(esp)).thenReturn(esp);

        Esp32 saved = service.create(esp);

        assertThat(saved.getId()).isEqualTo("x1");
        verify(repository).save(esp);
        verify(clientRepository).findByCc(100L); 
    }

    @Test
    void delete_should_call_repository() {
        Esp32 esp = new Esp32();
        esp.setId("d1");
        when(repository.findById("d1")).thenReturn(Optional.of(esp));

        service.delete("d1");
        verify(repository).deleteById("d1");
    }
}
