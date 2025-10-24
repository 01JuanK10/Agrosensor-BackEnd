package com.backend.agrosensor.agrosensorbackend.repository.Auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
      select t from Token t inner join AbstractUser u\s
      on t.user.cc = u.cc\s
      where u.cc = :id and (t.isExpired = false or t.isRevoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);
}