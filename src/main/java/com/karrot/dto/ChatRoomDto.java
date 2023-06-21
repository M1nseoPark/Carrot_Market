package com.karrot.dto;

import com.karrot.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDto {

    private Long id;

    private Member seller;

    @QueryProjection
    public ChatRoomDto(Long id, Member seller) {
        this.id = id;
        this.seller = seller;
    }
}
