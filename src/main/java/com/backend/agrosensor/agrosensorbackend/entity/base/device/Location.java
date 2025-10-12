package com.backend.agrosensor.agrosensorbackend.entity.base.device;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "device")
@Data
public class Location {
    @Id
    private Integer id;
    private Float latitude;
    private Float longitude;
    private String address;
}
