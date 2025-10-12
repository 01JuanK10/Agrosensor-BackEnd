package com.backend.agrosensor.agrosensorbackend.entity.impl.users;

import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import com.backend.agrosensor.agrosensorbackend.entity.base.device.Device;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "clients")
@Data
public class Client extends AbstractUser {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<Device> devices;
}
