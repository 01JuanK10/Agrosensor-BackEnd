package com.backend.agrosensor.agrosensorbackend.service.users.base;


import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;

import java.util.List;

public interface IUserService<T extends AbstractUser> {
    public T create(T user) throws RuntimeException;
    public T findByCc(Long cc) throws RuntimeException;
    public List<T> findAll();
    public T update(T user) throws RuntimeException;
    public void delete(Long cc) throws RuntimeException;
}
