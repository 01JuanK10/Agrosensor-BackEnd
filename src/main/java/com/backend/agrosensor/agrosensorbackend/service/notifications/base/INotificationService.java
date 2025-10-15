package com.backend.agrosensor.agrosensorbackend.service.notifications.base;

import com.backend.agrosensor.agrosensorbackend.entity.base.Notification;

import java.util.List;

public interface INotificationService<T extends Notification> {
    public T create(T notification) throws RuntimeException;
    public T findById(Long id) throws RuntimeException;
    public List<T> findAll();
    public T update(T notification) throws RuntimeException;
    public void delete(Long id) throws RuntimeException;
}
