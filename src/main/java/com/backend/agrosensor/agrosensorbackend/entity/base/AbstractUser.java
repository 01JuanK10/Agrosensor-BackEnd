package com.backend.agrosensor.agrosensorbackend.entity.base;

import com.backend.agrosensor.agrosensorbackend.repository.Auth.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AbstractUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cc;
    private String name;
    private String lastname;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Token> tokens;
}
