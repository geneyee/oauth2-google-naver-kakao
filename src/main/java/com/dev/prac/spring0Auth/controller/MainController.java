package com.dev.prac.spring0Auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(Model model) {

        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        model.addAttribute("id", id);
        model.addAttribute("role", role);

        return "main";
    }

    @GetMapping("/role")
    public String role(@AuthenticationPrincipal UserDetails user, Model model) {

        String id = user.getUsername();

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        String role = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .findAny()
                .orElse("");

        model.addAttribute("id", id);
        model.addAttribute("role", role);

        return "main";
    }

}
