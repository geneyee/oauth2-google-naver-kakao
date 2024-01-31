package com.dev.prac.spring0Auth.domain.upload;

import com.dev.prac.spring0Auth.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;

@ToString(exclude = "userEntity")
@NoArgsConstructor
@Getter
@Entity
public class UserImage implements Comparable<UserImage> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uuid;
    private String fileName;
    private int ord;

    @ManyToOne
    private UserEntity userEntity;

    @Builder
    public UserImage(String uuid, String fileName, int ord, UserEntity userEntity) {
        this.uuid = uuid;
        this.fileName = fileName;
        this.ord = ord;
        this.userEntity = userEntity;

    }
    @Override
    public int compareTo(UserImage other) {
        return this.ord - other.ord;
    }
    
    // 나중에 이미지 삭제 시
    public void changeUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
