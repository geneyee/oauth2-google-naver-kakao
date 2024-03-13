package com.dev.prac.spring0Auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Setter
@Getter
public class UploadFileDTO {

    private String fileName;
    private String contentType;

//    @Builder
//    public UploadFileDTO(String fileName, String contentType) {
//        this.fileName = fileName;
//        this.contentType = contentType;
//    }
}
