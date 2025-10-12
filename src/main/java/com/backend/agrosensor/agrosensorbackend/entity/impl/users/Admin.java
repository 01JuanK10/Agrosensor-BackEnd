package com.backend.agrosensor.agrosensorbackend.entity.impl.users;

import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "admins")

@Data
public class Admin extends AbstractUser {

    private String enterprise;
}
