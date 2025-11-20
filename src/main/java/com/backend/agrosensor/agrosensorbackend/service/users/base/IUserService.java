package com.backend.agrosensor.agrosensorbackend.service.users.base;


import java.util.List;
import java.util.Map;

import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;

public interface IUserService<T extends AbstractUser> {
    public T create(T user) throws RuntimeException;
    public T findByCc(Long cc) throws RuntimeException;
    public List<T> findAll();
    public T update(T user) throws RuntimeException;
    public void delete(Long cc) throws RuntimeException;
    public T patch(Long cc, Map<String, Object> updates) throws RuntimeException;

}
