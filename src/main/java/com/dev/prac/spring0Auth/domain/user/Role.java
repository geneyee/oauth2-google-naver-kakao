package com.dev.prac.spring0Auth.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    ADMIN("ROLE_ADMIN", "관리자"), USER("ROLE_USER", "일반사용자");

    private final String key;
    private final String title;

    @Override
    public String getAuthority() {
        return getKey();
    }
}
