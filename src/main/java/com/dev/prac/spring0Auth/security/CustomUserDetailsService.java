package com.dev.prac.spring0Auth.security;

import com.dev.prac.spring0Auth.domain.user.UserEntity;
import com.dev.prac.spring0Auth.domain.user.UserRepository;
import com.dev.prac.spring0Auth.security.dto.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity target = userRepository.findByUsername(username);

        if (target != null) {
            return new CustomUserDetails(target);
        }

        return null;
    }
}
