package com.karrot.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    private Long id;

    private String nick;

//    private String imgUrl;

    @Builder
    public MemberDto(Long id, String nick) {
        this.id = id;
        this.nick = nick;
//        this.imgUrl = imgUrl;
    }
}
