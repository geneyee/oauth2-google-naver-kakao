package com.dev.prac.spring0Auth.dto;

import com.dev.prac.spring0Auth.domain.user.Role;
import com.dev.prac.spring0Auth.domain.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO { // 일반 회원가입

    private String username;
    private String password;
    private String email;
    private Role role;
    private boolean social;

    public UserEntity toEntity() {
        return UserEntity.builder()
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .role(Role.USER)
                .social(false)
                .build();
    }

    public UserEntity toEntityAdmin() {
        return UserEntity.builder()
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .role(Role.ADMIN)
                .social(false)
                .build();
    }

}
