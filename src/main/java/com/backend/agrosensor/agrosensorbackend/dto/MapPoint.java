package com.backend.agrosensor.agrosensorbackend.dto;

public record MapPoint(
         Float latitude,
         Float longitude,
         String address,
         Float erosion
) {}
