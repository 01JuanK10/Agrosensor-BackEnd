package com.backend.agrosensor.agrosensorbackend.config;

import com.backend.agrosensor.agrosensorbackend.entity.base.AbstractUser;
import com.backend.agrosensor.agrosensorbackend.repository.users.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final IUserRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            final AbstractUser user = repository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            List<SimpleGrantedAuthority> authorities =
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
            return new User(
                    user.getUsername(),
                    user.getPassword(),
                    authorities
            );
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}