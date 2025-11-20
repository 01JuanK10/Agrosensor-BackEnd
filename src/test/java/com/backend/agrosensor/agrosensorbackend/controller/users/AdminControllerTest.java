package com.backend.agrosensor.agrosensorbackend.controller.users;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Admin;
import com.backend.agrosensor.agrosensorbackend.service.users.impl.AdminService;
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
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService service;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getAdminByCc_should_return_admin() throws Exception {
        Admin a = new Admin();
        a.setCc(10L);
        a.setName("Juan");

        when(service.findByCc(10L)).thenReturn(a);

        mockMvc.perform(get("/api/users/admins/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void findAllAdmins_should_return_list() throws Exception {
        Admin a = new Admin();
        a.setCc(10L);

        when(service.findAll()).thenReturn(List.of(a));

        mockMvc.perform(get("/api/users/admins"))
                .andExpect(status().isOk());
    }
}
