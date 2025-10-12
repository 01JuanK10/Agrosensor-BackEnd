package com.backend.agrosensor.agrosensorbackend.entity.impl.users;

import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")

public class Admin extends AbstractUser {
    private String propiedadAdmin;
}
