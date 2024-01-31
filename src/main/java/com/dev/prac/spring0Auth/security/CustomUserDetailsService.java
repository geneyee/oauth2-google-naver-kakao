package com.dev.prac.spring0Auth.security;

import com.dev.prac.spring0Auth.domain.user.UserEntity;
import com.dev.prac.spring0Auth.domain.user.UserRepository;
import com.dev.prac.spring0Auth.security.dto.CustomUserDetails;
import com.dev.prac.spring0Auth.security.dto.UserSecurityDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService { // 로그인

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> target = userRepository.findByUsername(username);

        // 회원 없으면 예외
        if (target.isEmpty()) {
            throw new UsernameNotFoundException("username not found");
        }

        UserEntity user = target.get();

        UserSecurityDTO userSecurityDTO = new UserSecurityDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                false,
                Collections.singleton(user.getRole()),
                user.getPicture());
        log.info("userSecurityDTO => {}", userSecurityDTO);

        return userSecurityDTO;

//        return new CustomUserDetails(user);
    }
}
