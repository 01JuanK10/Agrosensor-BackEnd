package com.backend.agrosensor.agrosensorbackend.controller.notifications;

import com.backend.agrosensor.agrosensorbackend.controller.notifications.AppNotificationController;
import com.backend.agrosensor.agrosensorbackend.entity.impl.notifications.AppNotification;
import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import com.backend.agrosensor.agrosensorbackend.service.notifications.impl.AppNotificationService;
import com.backend.agrosensor.agrosensorbackend.service.users.base.IUserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = AppNotificationController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        com.backend.agrosensor.agrosensorbackend.config.SecurityConfig.class,
                        com.backend.agrosensor.agrosensorbackend.config.JwtAuthenticationFilter.class
                }
        )
)
@AutoConfigureMockMvc(addFilters = false)
class AppNotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 👇 Ahora coincide EXACTAMENTE con el constructor
    @MockBean
    private AppNotificationService notificationService;

    @MockBean
    private IUserService<Client> clientService;

    @Test
    @WithMockUser(username = "camilo.alzate", roles = {"CLIENT"})
    void findAll_should_return_notifications() throws Exception {
        AppNotification n = new AppNotification();
        n.setId(1L);
        n.setMessage("Alert!");

        when(notificationService.findAll()).thenReturn(List.of(n));

        mockMvc.perform(get("/api/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].message").value("Alert!"));
    }

    @Test
    @WithMockUser(username = "camilo.alzate", roles = {"CLIENT"})
    void findById_should_return_notification() throws Exception {
        AppNotification n = new AppNotification();
        n.setId(1L);
        n.setMessage("Test notice");

        when(notificationService.findById(1L)).thenReturn(n);

        mockMvc.perform(get("/api/notifications/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Test notice"));
    }
}
