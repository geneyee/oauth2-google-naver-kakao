package com.dev.prac.spring0Auth.domain.user;

import com.dev.prac.spring0Auth.domain.upload.UserImage;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

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
    private String picture; // db에 저장할 파일 이름
    private boolean social;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    private Set<UserImage> imageSet;

//    private MultipartFile userImage; // 클라이언트가 보낸 파일 data 저장 객체

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

/*    // 이미지 추가
    public void addImage(String uuid, String fileName) {

        UserImage userImage = UserImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .ord(imageSet.size())
                .userEntity(this)
                .build();

        imageSet.add(userImage);
    }

    // 이미지 삭제
    public void clearImage() {
        imageSet.forEach(userImage -> userImage.changeUser(null));
        this.imageSet.clear();
    }*/

}
