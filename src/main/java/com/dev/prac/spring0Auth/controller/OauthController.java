package com.dev.prac.spring0Auth.controller;

import com.dev.prac.spring0Auth.security.dto.UserSecurityDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class OauthController {

    @GetMapping("/oauth")
    public ResponseEntity<UserSecurityDTO> oLogin(@AuthenticationPrincipal UserSecurityDTO dto) {
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/normal")
    public ResponseEntity<UserSecurityDTO> nLogin(@AuthenticationPrincipal UserSecurityDTO dto) {
        return ResponseEntity.ok(dto);
    }
}
