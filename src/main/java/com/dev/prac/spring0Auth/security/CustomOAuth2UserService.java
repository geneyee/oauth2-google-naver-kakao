package com.dev.prac.spring0Auth.security;

import com.dev.prac.spring0Auth.domain.user.UserEntity;
import com.dev.prac.spring0Auth.domain.user.UserRepository;
import com.dev.prac.spring0Auth.security.dto.OAuthAttributes;
import com.dev.prac.spring0Auth.security.dto.UserSecurityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인 서비스 구분(구글, 네이버, 카카오)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // id 키 값(pk)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        UserEntity user = saveOrUpdate(attributes);

        UserSecurityDTO userSecurityDTO = new UserSecurityDTO(user.getId(), user.getUsername(), passwordEncoder.encode("1234"), user.getEmail(), user.isSocial(),
                Collections.singleton(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return user.getRoleKey();
                    }
                }), user.getPicture());

        userSecurityDTO.updateAttributes(attributes.getAttributes());

        return userSecurityDTO;
    }

    private UserEntity saveOrUpdate(OAuthAttributes attributes) {
        UserEntity user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }


}
