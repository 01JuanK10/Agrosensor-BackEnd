package com.backend.agrosensor.agrosensorbackend.entity.impl.notifications;

import com.backend.agrosensor.agrosensorbackend.entity.base.Notification;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;


@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "app_notification")
public class AppNotification extends Notification {
}
