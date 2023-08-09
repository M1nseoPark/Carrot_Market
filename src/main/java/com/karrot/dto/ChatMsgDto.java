package com.karrot.dto;

import com.karrot.entity.ChatRoom;
import com.karrot.entity.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMsgDto {

    private Long id;

    private String sender;

    private String msg;

    private Long roomId;

    private Long itemId;
}
