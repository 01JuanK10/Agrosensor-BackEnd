package com.backend.agrosensor.agrosensorbackend.entity.base.device;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "device")
@Data
public abstract class Device {
    @Id
    private String id;
    private String model;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
