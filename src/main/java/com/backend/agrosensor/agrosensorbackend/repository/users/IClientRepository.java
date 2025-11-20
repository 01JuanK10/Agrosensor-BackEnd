package com.backend.agrosensor.agrosensorbackend.repository.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;

@Repository
public interface IClientRepository extends JpaRepository<Client,Long> {
    Optional<Client> findByCc(Long cc);
}
