package com.backend.agrosensor.agrosensorbackend.repository.users;

import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<AbstractUser, Long> {
    Optional<AbstractUser> findByUsername(String username);

}
