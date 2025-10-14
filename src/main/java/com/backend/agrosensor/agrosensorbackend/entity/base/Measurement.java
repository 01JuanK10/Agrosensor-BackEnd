package com.backend.agrosensor.agrosensorbackend.entity.base;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "measurement")
public abstract class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dateTime;
    private Float environmentMoisture;
    private Float environmentTemperature;

}
