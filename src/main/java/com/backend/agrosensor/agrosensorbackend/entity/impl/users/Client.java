package com.backend.agrosensor.agrosensorbackend.entity.impl.users;

import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import com.backend.agrosensor.agrosensorbackend.entity.base.Notification;
import com.backend.agrosensor.agrosensorbackend.entity.base.device.Device;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "clients")
@Data
public class Client extends AbstractUser {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<Device> devices;

    @OneToMany
    @JoinColumn(name = "notification_id")
    private List<Notification> notifications;

}
