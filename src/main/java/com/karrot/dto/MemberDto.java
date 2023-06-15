package com.karrot.dto;

import com.karrot.entity.MemberImg;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    private Long id;

    private String nick;

    private MemberImg imgUrl;

    private Long memberImgId;

    @Builder
    public MemberDto(Long id, String nick, MemberImg imgUrl) {
        this.id = id;
        this.nick = nick;
        this.imgUrl = imgUrl;
    }
}
