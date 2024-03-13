package com.dev.prac.spring0Auth.security.dto;

import com.dev.prac.spring0Auth.domain.upload.UserImage;
import com.dev.prac.spring0Auth.domain.user.UserEntity;
import lombok.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.multipart.MultipartFile;

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
    private String picture; // 이미지 파일 이름
    private MultipartFile uploadFile; // 이미지 파일

    @Builder
    public UserRequestDTO(String username, String password, String email, String picture) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.picture = picture;
    }

    public UserRequestDTO toForm(Integer id, String username, String password, String email, String picture) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.picture = picture;
        return this;
    }

    // entity to dto
    public static UserRequestDTO of(UserEntity user) {
        return UserRequestDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .picture(user.getPicture())
                .build();
    }

    // dto to entity
    public UserEntity toEntity(UserRequestDTO userRequestDTO) {

        UserEntity entity = UserEntity.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

/*        if (userRequestDTO.getPicture() != null) {
            String[] arr = userRequestDTO.getPicture().split("_");
            entity.addImage(arr[0], arr[1]);
        }*/
        return entity;
    }

}
