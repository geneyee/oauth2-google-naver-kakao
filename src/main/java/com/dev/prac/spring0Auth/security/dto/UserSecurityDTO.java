package com.dev.prac.spring0Auth.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@ToString
@Setter
@Getter
public class UserSecurityDTO extends User implements OAuth2User {

    private String username;
    private String password;
    private String email;
    private boolean social;

    private Map<String, Object> props;

    public UserSecurityDTO(String username, String password, String email, boolean social, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.username = username;
        this.password = password;
        this.social = social;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.getProps();
    }

    @Override
    public String getName() {
        return this.username;
    }
}
