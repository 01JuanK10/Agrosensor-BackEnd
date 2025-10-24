package com.backend.agrosensor.agrosensorbackend.controller.utilities;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/utilities")
public class ServerTimeController {

    @GetMapping("/server-time")
    public Map<String, String> getServerTime() {
        String dateTime = java.time.LocalDateTime.now().toString();
        return Map.of("dateTime", dateTime);
    }
}
