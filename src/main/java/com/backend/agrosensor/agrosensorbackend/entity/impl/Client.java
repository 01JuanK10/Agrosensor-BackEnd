package com.backend.agrosensor.agrosensorbackend.entity.impl;

import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "clients")
public class Client extends AbstractUser {
    private String propiedadCliente;
}
