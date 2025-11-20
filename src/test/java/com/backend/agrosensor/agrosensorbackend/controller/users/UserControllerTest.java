package com.backend.agrosensor.agrosensorbackend.controller.users;

import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import com.backend.agrosensor.agrosensorbackend.repository.users.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserRepository repository;

    @Test
    @WithMockUser(username = "camilo.alzate", roles = {"CLIENT"})
    void resetPassword_should_update_password() throws Exception {
        AbstractUser u = new AbstractUser();
        u.setUsername("john");

        when(repository.findByUsername("john")).thenReturn(Optional.of(u));

        mockMvc.perform(post("/api/users/reset-password/john")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"password":"newpass"}
                                """))
                .andExpect(status().isOk());
    }
}
