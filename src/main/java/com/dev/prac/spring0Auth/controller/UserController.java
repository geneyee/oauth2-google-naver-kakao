package com.dev.prac.spring0Auth.controller;

import com.dev.prac.spring0Auth.domain.user.UserEntity;
import com.dev.prac.spring0Auth.dto.JoinDTO;
import com.dev.prac.spring0Auth.dto.UploadFileDTO;
import com.dev.prac.spring0Auth.exception.UsernameExistException;
import com.dev.prac.spring0Auth.security.dto.UserRequestDTO;
import com.dev.prac.spring0Auth.security.dto.UserSecurityDTO;
import com.dev.prac.spring0Auth.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO, RedirectAttributes redirectAttributes) {
        try {
            userService.join(joinDTO);
        } catch (UsernameExistException e) {
            redirectAttributes.addFlashAttribute("error", "username");
            return "redirect:/join";
        }
        return "redirect:/login";
    }

    @GetMapping("/checkUsername/{username}")
    @ResponseBody
    public ResponseEntity<Boolean> checkUsername(@PathVariable String username) {
        boolean result = userService.checkUsername(username); // 아이디 있으면 true 없으면 false
        log.info(result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<UserEntity> userList = new ArrayList<>();
        userList = userService.userList();
        model.addAttribute("userList", userList);
        return "admin";
    }

    @GetMapping("/social")
    public String social(@AuthenticationPrincipal UserSecurityDTO userSecurityDTO, Model model) {

        model.addAttribute("user", userSecurityDTO);

        return "social";
    }

    @GetMapping("/normal")
//    public String normal(@AuthenticationPrincipal UserSecurityDTO userSecurityDTO, Model model) {
    public String normal(@AuthenticationPrincipal UserDetails user, Model model) {

        model.addAttribute("user", user);

        return "normal";
    }

    @GetMapping("/modify")
    public String modifyForm(@AuthenticationPrincipal UserSecurityDTO dto, UserRequestDTO requestDTO, Model model){
        log.info("로그인 회원 id => {}", dto.getId());
        log.info("social => {}", dto.isSocial());

        requestDTO.toForm(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getEmail(), dto.getPicture());
        model.addAttribute("userRequestDTO", requestDTO);
        return "user_page";
    }

    @PostMapping("/modify/{id}")
    public String modify(UserRequestDTO dto, @PathVariable Integer id) {

        UserRequestDTO userDTO = userService.getId(id);
        log.info(userDTO);

        UploadFileDTO profile = userService.uploadFile(dto.getUploadFile());
        dto.setPicture(profile.getFileName());
        UserRequestDTO update = userService.modify(id, dto);

        return "redirect:/modify";
    }

    @PostMapping("/user/{id}")
    public String userPage(UserRequestDTO dto, @PathVariable Integer id) {

        UserRequestDTO userDTO = userService.getId(id);
        log.info(userDTO);

        userService.modify(id, dto);

        return "redirect:/modify";
    }

}
