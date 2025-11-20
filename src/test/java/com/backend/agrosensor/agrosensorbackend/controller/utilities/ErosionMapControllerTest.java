package com.backend.agrosensor.agrosensorbackend.controller.utilities;

import com.backend.agrosensor.agrosensorbackend.dto.MapPoint;
import com.backend.agrosensor.agrosensorbackend.service.utilities.ErosionPointService;
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
class ErosionMapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ErosionPointService service;

    @Test
    @WithMockUser(username = "camilo.alzate", roles = {"CLIENT"})
    void getErosionPoints_should_return_list() throws Exception {
        MapPoint p = new MapPoint(10f, 20f, "farm", 30f);

        when(service.getErosionPointsByClient(100L)).thenReturn(List.of(p));

        mockMvc.perform(get("/api/map/erosion-points/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("farm"));
    }
}
