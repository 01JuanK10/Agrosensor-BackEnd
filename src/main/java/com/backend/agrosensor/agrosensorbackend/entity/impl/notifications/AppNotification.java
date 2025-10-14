package com.backend.agrosensor.agrosensorbackend.entity.impl.notifications;

import com.backend.agrosensor.agrosensorbackend.entity.base.Notification;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "app_notification")
public class AppNotification extends Notification {
}
