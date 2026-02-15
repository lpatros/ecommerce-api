package com.lpatros.ecommerce_api.configuration;

import com.lpatros.ecommerce_api.exception.NotFoundException;
import com.lpatros.ecommerce_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthConfig implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AuthConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> new NotFoundException("User", "email"));
    }
}
