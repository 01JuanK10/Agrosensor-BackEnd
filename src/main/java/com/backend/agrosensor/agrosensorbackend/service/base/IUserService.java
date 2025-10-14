package com.backend.agrosensor.agrosensorbackend.service.base;


import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;

import java.util.List;

public interface IUserService<T extends AbstractUser> {
    public T create(T user);
    public T findByCc(Long cc);
    public List<T> findAll();
    public T update(T user);
    public void delete(T user);
}
