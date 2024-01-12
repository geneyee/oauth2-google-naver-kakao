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

//        UserSecurityDTO dto = (UserSecurityDTO) authentication.getPrincipal();

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("getDetails => {}", userDetails);

        if(userDetails.isSocial()){
            response.sendRedirect("/social");
            return;
        } else {
            response.sendRedirect("/normal");
        }
    }
}
