package com.dev.prac.spring0Auth.service;

import com.dev.prac.spring0Auth.domain.user.UserEntity;
import com.dev.prac.spring0Auth.domain.user.UserRepository;
import com.dev.prac.spring0Auth.dto.JoinDTO;
import com.dev.prac.spring0Auth.exception.UsernameExistException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    // join과 분리한 이유 - html에서 js로 중복검사 버튼 만들려고
    public boolean checkUsername(String username){
       boolean exist = userRepository.existsByUsername(username);
       log.info(exist);
       return exist; // 있으면 true, 없으면 false
    }

    public void join(JoinDTO joinDTO) {

        // db에 동일한 username을 가진 회원이 있는지 검증
        String username = joinDTO.getUsername();

        boolean exist = checkUsername(username);

        if(exist) {
            throw new UsernameExistException("username exist");
        }

        // 아이디 admin이면 role.ADMIN 나머지는 role.USER
        if(username.equals("admin")){
            UserEntity user = joinDTO.toEntityAdmin();
            user.encodePassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } else {
            UserEntity user = joinDTO.toEntity();
            user.encodePassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }
}
