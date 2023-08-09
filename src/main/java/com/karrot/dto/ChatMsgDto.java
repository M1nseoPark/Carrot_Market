package com.karrot.dto;

import com.karrot.entity.ChatMsg;
import com.karrot.entity.ChatRoom;
import com.karrot.entity.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMsgDto {

    private String sender;

    private String msg;

    private Long roomId;

    private Long ItemId;

    public ChatMsg createChat(String sender, String msg, ChatRoomDto chatRoomDto) {
        return ChatMsg.builder()
                .sender(sender)
                .msg(msg)
                .chatRoom(chatRoomDto.toEntity())
                .build();
    }
}
