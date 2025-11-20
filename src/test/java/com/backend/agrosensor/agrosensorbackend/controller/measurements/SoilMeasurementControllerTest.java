package com.backend.agrosensor.agrosensorbackend.controller.measurements;

import com.backend.agrosensor.agrosensorbackend.entity.impl.measurements.SoilMeasurement;
import com.backend.agrosensor.agrosensorbackend.service.measurements.impl.SoilMeasurementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class SoilMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SoilMeasurementService service;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getAll_should_return_list() throws Exception {
        SoilMeasurement m = new SoilMeasurement();
        m.setErosion(20f);

        when(service.findAll()).thenReturn(List.of(m));

        mockMvc.perform(get("/api/measurements/soil"))
                .andExpect(status().isOk());
    }
}
