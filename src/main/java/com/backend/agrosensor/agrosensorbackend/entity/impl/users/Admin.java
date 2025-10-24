package com.backend.agrosensor.agrosensorbackend.entity.impl.users;

import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admins")

@Data
@AllArgsConstructor
public class Admin extends AbstractUser {
}
