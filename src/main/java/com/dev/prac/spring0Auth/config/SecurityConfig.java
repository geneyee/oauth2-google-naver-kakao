package com.dev.prac.spring0Auth.config;

import com.dev.prac.spring0Auth.domain.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
                        .requestMatchers("/image/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/", "/login/**", "/join", "/checkUsername/**", "/role").permitAll()
                        .requestMatchers("/admin").hasRole(Role.ADMIN.name()) // "ADMIN"
                        .requestMatchers("/my/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name()) //"ADMIN", "USER"
                        .anyRequest().authenticated());

        http
                .formLogin((formLogin) -> formLogin
                        .loginPage("/login")
                        .permitAll()
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/"));

        // 동일한 아이디로 다중 로그인 진행할 시(다른 브라우저)
        http
                .sessionManagement((session) -> session
                        .maximumSessions(1) // 하나의 아이디에 대한 다중 로그인 허융개수
                        .maxSessionsPreventsLogin(false)); // 다중 로그인 개수 초과시 기존 세션 삭제 // true : 새로운 로그인 차단
        
        // https://substantial-park-a17.notion.site/10-36136f5a91f647b499dbcb5a884aff72
        // 세션 고정 공격 보호
        http
                .sessionManagement((session) -> session
                        .sessionFixation().changeSessionId());

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
}
