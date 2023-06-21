package com.karrot.repository;

import com.karrot.dto.ChatRoomDto;
import com.karrot.entity.Member;

import java.util.List;

public interface ChatRoomRepositoryCustom {

    List<ChatRoomDto> getChatRoomList(Long roomId);

    List<ChatRoomDto> getChatRoomId(Long itemId, Member seller);
}
