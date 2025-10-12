package com.backend.agrosensor.agrosensorbackend.entity.base;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@Data
public abstract class AbstractUser {
    @Id
    private Integer cc;
    private String name;
    private String lastname;
    private String username;
    private String password;
    private String role;
}
