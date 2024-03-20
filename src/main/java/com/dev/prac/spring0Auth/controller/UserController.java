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
import org.springframework.web.bind.annotation.*;
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

        // entity에 저장된 picture 가지고 오기
        UserRequestDTO userRequestDTO = userService.getId(dto.getId());
        log.info("프로필 => {}", userRequestDTO.getPicture());

        // 1. securityDTO.picture와 userRequestDTO.picture 비교
        // 2. 같으면 .toForm()
        // 3. 다르면 userRequestDTO.picture 업로드..
        // 3-1. 경로 c:\\upload -> 여기서 경로 붙여서 보내야함 *{picture}

        if (dto.getPicture() != null && userRequestDTO.getPicture() != null) { // 모두 null 아닐때
            if (dto.getPicture().equals(userRequestDTO.getPicture())) { // 이름 같을 때
                requestDTO.toForm(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getEmail(), dto.getPicture());
            } else { // 이름 다른 경우
                String newProfile = "/upload/"+ userRequestDTO.getPicture();
                requestDTO.toForm(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getEmail(), newProfile);
            }
        } /* else if (dto.getPicture() == null || userRequestDTO.getPicture() == null) { // 둘 중 하나가 null 일 때
            throw new NullPointerException();
        }*/ // null일 때 검증 user_page에서 하고 있음
        // Object로 하는 방법도 있음 -> null 체크 해줌

        // security dto의 데이터를 UserRequestDTO에 저장하고 있다..
        // 바뀐 프로필 이미지를 보여주려면 이것을 수정해야함
        // dto picture 이름을 비교해서 다르면 다른애를 보여주는 방법?

        // 1. UserRequestDTO 의 id로 entity 찾기    userService.getId(requestDTO.getId()); -> redirect하기 떄문에 x?
        // 1-1. html form에서 picture hidden으로 보내기? (이미 보내고 있음)
        // 1-2. hidden으로 가져온 값 controller에서 받기
        // 2. UserSecurityDTO 정보 가져오기
        // 3. 1과 2의 picture의 이름 비교하기
        // 4. 같으면 requestDTO.toForm 다르면 UserRequestDTO의 picture를 저장..

        model.addAttribute("userRequestDTO", requestDTO);
        return "user_page";
    }

    @PostMapping("/modify/{id}")
    public String modify(UserRequestDTO dto, @PathVariable Integer id) {

        UserRequestDTO userDTO = userService.getId(id);
        log.info(userDTO);

//        UploadFileDTO profile = userService.uploadFile(dto.getUploadFile());
//        dto.setPicture(profile.getFileName());
        userService.modify(id, dto);

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
