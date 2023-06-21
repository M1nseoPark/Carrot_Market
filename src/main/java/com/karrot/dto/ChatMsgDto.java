package com.karrot.dto;

import com.karrot.entity.ChatRoom;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMsgDto {

    private Long id;

    private String sender;

    private String msg;

    private ChatRoom chatRoom;
}
