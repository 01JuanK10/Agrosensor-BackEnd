package com.backend.agrosensor.agrosensorbackend.controller.users;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.service.users.impl.ClientService;
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
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    @WithMockUser(username = "camilo.alzate", roles = {"CLIENT"})
    void findByCc_should_return_client() throws Exception {
        Client c = new Client();
        c.setCc(90L);
        c.setName("Maria");

        when(clientService.findByCc(90L)).thenReturn(c);

        mockMvc.perform(get("/api/users/clients/90"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maria"));
    }

    @Test
    @WithMockUser(username = "camilo.alzate", roles = {"CLIENT"})
    void findAll_should_return_list() throws Exception {
        Client c = new Client();
        c.setCc(90L);

        when(clientService.findAll()).thenReturn(List.of(c));

        mockMvc.perform(get("/api/users/clients"))
                .andExpect(status().isOk());
    }
}
