package com.backend.agrosensor.agrosensorbackend.service.devices.base;

import com.backend.agrosensor.agrosensorbackend.entity.base.device.Device;

import java.util.List;

public interface IDeviceService<T extends Device> {
    public T create(T device) throws RuntimeException;
    public T findById(String id) throws RuntimeException;
    public List<T> findAll();
    public T update(T device) throws RuntimeException;
    public void delete(String id) throws RuntimeException;
}
