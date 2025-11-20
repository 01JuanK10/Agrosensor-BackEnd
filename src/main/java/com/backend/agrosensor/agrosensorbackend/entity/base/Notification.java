package com.backend.agrosensor.agrosensorbackend.entity.base;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
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

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
