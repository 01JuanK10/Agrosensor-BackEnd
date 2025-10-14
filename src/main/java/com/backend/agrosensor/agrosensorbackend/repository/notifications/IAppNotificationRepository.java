package com.backend.agrosensor.agrosensorbackend.repository.notifications;

import com.backend.agrosensor.agrosensorbackend.entity.impl.notifications.AppNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppNotificationRepository extends JpaRepository<AppNotification,Integer> {
}
