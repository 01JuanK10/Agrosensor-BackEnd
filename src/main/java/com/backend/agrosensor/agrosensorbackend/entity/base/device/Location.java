package com.backend.agrosensor.agrosensorbackend.entity.base.device;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "location")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Float latitude;
    private Float longitude;
    private String address;
}
