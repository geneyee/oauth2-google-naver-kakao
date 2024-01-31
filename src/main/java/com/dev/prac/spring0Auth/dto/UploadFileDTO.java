package com.dev.prac.spring0Auth.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UploadFileDTO {

    private MultipartFile file;
}
