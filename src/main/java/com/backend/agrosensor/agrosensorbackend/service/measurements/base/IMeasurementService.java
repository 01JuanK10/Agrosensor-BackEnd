package com.backend.agrosensor.agrosensorbackend.service.measurements.base;

import com.backend.agrosensor.agrosensorbackend.entity.base.Measurement;

import java.util.List;

public interface IMeasurementService<T extends Measurement> {
    public T create(T measurement) throws RuntimeException;
    public List<T> findAll();
}
