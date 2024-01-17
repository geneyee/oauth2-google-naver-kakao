package com.dev.prac.spring0Auth.security.dto;

import com.dev.prac.spring0Auth.domain.user.UserEntity;
import lombok.*;

import java.util.Collections;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class UserRequestDTO {

    private Integer id;
    private String username;
    private String password;
    private String email;

    @Builder
    public UserRequestDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserRequestDTO toForm(Integer id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        return this;
    }

    public static UserRequestDTO of(UserEntity user) {
        return new UserRequestDTO(user.getUsername(), user.getPassword(), user.getEmail());
    }


}
