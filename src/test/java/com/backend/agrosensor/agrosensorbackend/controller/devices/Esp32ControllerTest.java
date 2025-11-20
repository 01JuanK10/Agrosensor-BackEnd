package com.backend.agrosensor.agrosensorbackend.controller.devices;

import com.backend.agrosensor.agrosensorbackend.entity.impl.devices.Esp32;
import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.service.devices.impl.Esp32Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class Esp32ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Esp32Service service;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void findAll_should_return_list() throws Exception {
        Esp32 esp = new Esp32();
        esp.setId("DEV01");
        esp.setClient(new Client());

        when(service.findAll()).thenReturn(List.of(esp));

        mockMvc.perform(get("/api/devices/esp32"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("DEV01"));
    }
}
