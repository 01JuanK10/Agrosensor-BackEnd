package com.backend.agrosensor.agrosensorbackend.entity.base;

import com.backend.agrosensor.agrosensorbackend.entity.base.device.Device;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Table(name = "measurement")
public abstract class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dateTime;
    private Float environmentMoisture;
    private Float environmentTemperature;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @OneToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;

}
