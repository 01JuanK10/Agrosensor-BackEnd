package com.backend.agrosensor.agrosensorbackend.controller.users;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import com.backend.agrosensor.agrosensorbackend.repository.users.IUserRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/reset-password/{username}")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body, @PathVariable String username) {
        Optional<AbstractUser> userOptional = repository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with username '" + username + "' not found.");
        }

        AbstractUser user = userOptional.get();
        String password = body.get("password");

        if (password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body("Password cannot be empty.");
        }

        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);

        repository.save(user);

        return ResponseEntity.ok("Password updated successfully.");
    }

    

}
