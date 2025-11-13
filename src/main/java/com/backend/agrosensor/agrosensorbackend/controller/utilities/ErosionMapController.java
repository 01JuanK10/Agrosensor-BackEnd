package com.backend.agrosensor.agrosensorbackend.controller.utilities;

import com.backend.agrosensor.agrosensorbackend.dto.MapPoint;
import com.backend.agrosensor.agrosensorbackend.service.utilities.ErosionPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/map/erosion-points")
@RequiredArgsConstructor
public class ErosionMapController {

    private final ErosionPointService erosionPointService;

    @GetMapping("/{cedula}")
    public ResponseEntity<List<MapPoint>> getErosionPointsByClient(@PathVariable Long cedula) {
        List<MapPoint> points = erosionPointService.getErosionPointsByClient(cedula);
        return ResponseEntity.ok(points);
    }
}
