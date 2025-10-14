package com.backend.agrosensor.agrosensorbackend.entity.base;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "notification")
public abstract class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean readState;
    private String message;

    public Notification() {
        this.readState = false;
    }
}
