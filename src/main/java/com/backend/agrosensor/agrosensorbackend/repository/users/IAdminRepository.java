package com.backend.agrosensor.agrosensorbackend.repository.users;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdminRepository extends JpaRepository<Admin,Integer> {
}
