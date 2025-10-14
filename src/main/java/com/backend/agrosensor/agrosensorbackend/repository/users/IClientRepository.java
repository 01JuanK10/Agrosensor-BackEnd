package com.backend.agrosensor.agrosensorbackend.repository.users;

import com.backend.agrosensor.agrosensorbackend.entity.impl.users.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<Client,Integer> {
}
