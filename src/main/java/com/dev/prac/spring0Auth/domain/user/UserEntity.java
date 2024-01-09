package com.dev.prac.spring0Auth.domain.user;

import jakarta.persistence.*;
import lombok.*;

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
}
