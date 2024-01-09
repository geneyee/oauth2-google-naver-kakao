package com.dev.prac.spring0Auth.config;

import com.dev.prac.spring0Auth.domain.user.Role;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable())
                .headers((header) ->
                        header.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()));

        http
                .authorizeHttpRequests((request)-> request
                        .requestMatchers("/", "/login", "/join", "/checkUsername/**").permitAll()
                        .requestMatchers("/admin").hasRole(Role.ADMIN.name()) // "ADMIN"
                        .requestMatchers("/my/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name()) //"ADMIN", "USER"
                        .anyRequest().authenticated());

        http
                .formLogin((formLogin) -> formLogin
                        .loginPage("/login")
                        .permitAll());



        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적자원 스프링시큐리티 적용 제외
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
