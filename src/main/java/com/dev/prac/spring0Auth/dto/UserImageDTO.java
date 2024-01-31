package com.dev.prac.spring0Auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserImageDTO {

    private String uuid;
    private String fileName;
    private int ord;

    @Builder
    public UserImageDTO(String uuid, String fileName, int ord) {
        this.uuid = uuid;
        this.fileName = fileName;
        this.ord = ord;
    }
}
