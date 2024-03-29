package com.karrot.dto;

import com.karrot.entity.ChatRoom;
import com.karrot.entity.Item;
import com.karrot.entity.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ChatRoomDto {

    private Long id;

    private Member seller;

    private Set<WebSocketSession> session = new HashSet<>();

    private Item item;

    @QueryProjection
    public ChatRoomDto(Long id, Member seller) {
        this.id = id;
        this.seller = seller;
    }

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .seller(seller)
                .item(item)
                .build();
    }
}
