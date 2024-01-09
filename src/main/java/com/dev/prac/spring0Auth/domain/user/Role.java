package com.dev.prac.spring0Auth.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"), USER("ROLE_USER", "일반사용자");

    private final String key;
    private final String title;
}