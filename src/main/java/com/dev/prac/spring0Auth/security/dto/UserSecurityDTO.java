package com.dev.prac.spring0Auth.security.dto;

import com.dev.prac.spring0Auth.domain.user.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@ToString
@Setter
@Getter
public class UserSecurityDTO extends User implements OAuth2User {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private boolean social;

    private Map<String, Object> attributes;

    public UserSecurityDTO(Integer id, String username, String password, String email, boolean social,
                           Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.social = social;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.username;
    }

    public void updateAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

}
