package com.backend.agrosensor.agrosensorbackend.controller.utilities;

import com.backend.agrosensor.agrosensorbackend.service.utilities.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Test
    void sendEmail_should_return_ok() throws Exception {
        when(emailService.sendEmail("test@mail.com")).thenReturn(true);

        mockMvc.perform(get("/utilities/send/test@mail.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Correo enviado correctamente a: test@mail.com"));
    }
}
