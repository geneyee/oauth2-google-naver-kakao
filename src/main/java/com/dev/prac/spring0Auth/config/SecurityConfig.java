package com.dev.prac.spring0Auth.config;

import com.dev.prac.spring0Auth.domain.user.Role;
import com.dev.prac.spring0Auth.security.CustomOAuth2UserService;
import com.dev.prac.spring0Auth.security.CustomUserDetailsService;
import com.dev.prac.spring0Auth.security.handler.CustomSocialLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.mutation.internal.temptable.PersistentTableInsertStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    private final CustomOAuth2UserService customOAuth2UserService;
    private final DataSource dataSource;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable())
                .headers((header) ->
                        header.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()));

        http
                .authorizeHttpRequests((request)-> request
                        .requestMatchers("/image/**", "/css/**", "/js/**", "/upload/**").permitAll()
                        .requestMatchers("/", "/login/**", "/join", "/checkUsername/**", "/role").permitAll()
                        .requestMatchers("/admin").hasRole(Role.ADMIN.name()) // "ADMIN"
                        .requestMatchers("/my/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name()) //"ADMIN", "USER"
                        .anyRequest().authenticated());

        http
                .formLogin((formLogin) -> formLogin
                        .loginPage("/login")
                        .successHandler(authenticationSuccessHandler()));

        http
                .oauth2Login((oauth2Login) -> oauth2Login
                        .loginPage("/login")
                        .successHandler(authenticationSuccessHandler()));

        http
                .rememberMe((remember -> remember
                        .key("rememberMeKey")
                        .tokenRepository(persistentTokenRepository())
                        .userDetailsService(userDetailsService)
                        .tokenValiditySeconds(60 * 60 * 24 * 30))); // 한달


        // 동일한 아이디로 다중 로그인 진행할 시(다른 브라우저)
//        http
//                .sessionManagement((session) -> session
//                        .maximumSessions(1) // 하나의 아이디에 대한 다중 로그인 허융개수
//                        .maxSessionsPreventsLogin(false)); // 다중 로그인 개수 초과시 기존 세션 삭제 // true : 새로운 로그인 차단
//
//        // https://substantial-park-a17.notion.site/10-36136f5a91f647b499dbcb5a884aff72
//        // 세션 고정 공격 보호
//        http
//                .sessionManagement((session) -> session
//                        .sessionFixation().changeSessionId());

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // 정적자원 스프링시큐리티 적용 제외
//        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomSocialLoginSuccessHandler();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

}
