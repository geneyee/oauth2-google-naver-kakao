package com.dev.prac.spring0Auth.security.handler;

import com.dev.prac.spring0Auth.domain.user.UserEntity;
import com.dev.prac.spring0Auth.security.dto.CustomUserDetails;
import com.dev.prac.spring0Auth.security.dto.UserSecurityDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Log4j2
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        UserSecurityDTO dto = (UserSecurityDTO) authentication.getPrincipal();

//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("getDetails => {}", dto);

        // 관리자 -> 관리자 페이지 나머지 일반회원은 정보수정 페이지. 소셜로그인 회원은 비밀번호 수정 필수
        if (dto.getUsername().equals("admin")) {
            response.sendRedirect("/admin");
        } else {
            response.sendRedirect("/modify");
        }

        // 소셜 로그인 확인
//        if (dto.isSocial()) {
//            response.sendRedirect("/modify");
//            return;
//        } else {
//            response.sendRedirect("/normal");
//        }

/*
        // ResponseEntity.ok(dto)
        if(dto.isSocial()){
            response.sendRedirect("/api/oauth");
            return;
        } else {
            response.sendRedirect("/api/normal");
        }*/
    }
}
