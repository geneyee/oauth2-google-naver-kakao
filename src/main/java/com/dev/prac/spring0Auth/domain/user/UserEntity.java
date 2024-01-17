package com.dev.prac.spring0Auth.domain.user;

import com.dev.prac.spring0Auth.security.dto.UserRequestDTO;
import com.dev.prac.spring0Auth.security.dto.UserSecurityDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.User;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
    private String email;
    private String picture;
    private boolean social;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public UserEntity(String username, String password, String email, String picture, boolean social, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.picture = picture;
        this.social = social;
        this.role = role;
    }

    public void encodePassword(String password){
        this.password = password;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    // oauth 로그인 - email 있으면 update
    public UserEntity update(String username, String picture) {
        this.username = username;
        this.picture = picture;
        return this;
    }

    // 정보수정 - 비밀번호
    public UserEntity modify(String password) {
        this.password = password;
        return this;
    }

}
