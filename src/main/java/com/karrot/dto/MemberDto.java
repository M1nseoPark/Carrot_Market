package com.karrot.dto;

import com.karrot.entity.MemberImg;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberDto {

    private Long id;

    private String nick;

    private MemberImg imgUrl;

    private Long memberImgId;

    private List<Long> likeItem;

    @Builder
    public MemberDto(Long id, String nick, MemberImg imgUrl, List<Long> likeItem) {
        this.id = id;
        this.nick = nick;
        this.imgUrl = imgUrl;
        this.likeItem = likeItem;
    }
}
