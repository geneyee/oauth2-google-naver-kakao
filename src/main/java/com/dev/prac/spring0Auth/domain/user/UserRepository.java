package com.dev.prac.spring0Auth.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    // 회원가입 시 username 존재여부, 있으면 true 없으면 false
    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);
}
