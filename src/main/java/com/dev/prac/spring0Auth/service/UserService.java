package com.dev.prac.spring0Auth.service;

import com.dev.prac.spring0Auth.domain.user.Role;
import com.dev.prac.spring0Auth.domain.user.UserEntity;
import com.dev.prac.spring0Auth.domain.user.UserRepository;
import com.dev.prac.spring0Auth.dto.JoinDTO;
import com.dev.prac.spring0Auth.dto.UploadFileDTO;
import com.dev.prac.spring0Auth.exception.UsernameExistException;
import com.dev.prac.spring0Auth.security.dto.UserRequestDTO;
import com.dev.prac.spring0Auth.security.dto.UserSecurityDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    // join과 분리한 이유 - html에서 js로 중복검사 버튼 만들려고
    public boolean checkUsername(String username){
       boolean exist = userRepository.existsByUsername(username);
       log.info(exist);
       return exist; // 있으면 true, 없으면 false
    }

    public void join(JoinDTO joinDTO) {

        // db에 동일한 username을 가진 회원이 있는지 검증
        String username = joinDTO.getUsername();

        boolean exist = checkUsername(username);

        if(exist) {
            throw new UsernameExistException("username exist");
        }

        // 아이디 admin이면 Role.ADMIN 나머지 Role.USER
        Role role = username.equals("admin") ? Role.ADMIN : Role.USER;
        UserEntity user = joinDTO.toEntity(role); // dto to entity
        user.encodePassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 암호화
        
        userRepository.save(user); // db 저장
    }

    // id로 조회 후 entity -> dto
    public UserRequestDTO getId(Integer id) {

        UserEntity entity = userRepository.findById(id).orElseThrow();
        log.info(entity);

        return UserRequestDTO.of(entity);
    }

    public UserRequestDTO modify(Integer id, UserRequestDTO dto) {
        log.info("id => {}", id);
        log.info("비밀번호 수정 dto => {}", dto);

        Optional<UserEntity> target = userRepository.findById(id);

        if(target.isEmpty()){
            throw new NoSuchElementException();
        }

        UserEntity entity = target.get();
        log.info("비밀번호 수정 전 => {}", entity.getPassword());

        entity.modify(passwordEncoder.encode(dto.getPassword()));

        UserEntity save = userRepository.save(entity);
        log.info("비밀번호 수정 후 => {}", entity.getPassword());

        return UserRequestDTO.of(save);
    }

    public List<UserEntity> userList() {
        List<UserEntity> userList = userRepository.findByAllDesc();
        return userList;
    }

    // 프로필 이미지 수정
    public UploadFileDTO uploadFile(MultipartFile uploadFile) {

//        UploadFileDTO dto = UploadFileDTO.builder()
//                .fileName(uploadFile.getOriginalFilename())
//                .contentType(uploadFile.getContentType())
//                .build();

        UploadFileDTO dto = new UploadFileDTO();
        dto.setFileName(uploadFile.getOriginalFilename());

        File newFile = new File("c:\\upload" + "/" + dto.getFileName());

        try {
            uploadFile.transferTo(newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    // 해야할거
    // 위 방법 말고 스프링으로 했던거 참고해서 하면 될듯??
}
