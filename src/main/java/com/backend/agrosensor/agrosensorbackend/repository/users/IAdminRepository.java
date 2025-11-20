package com.backend.agrosensor.agrosensorbackend.repository.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Admin;

@Repository
public interface IAdminRepository extends JpaRepository<Admin,Long> {
    Optional<Admin> findByCc(Long cc);

}
