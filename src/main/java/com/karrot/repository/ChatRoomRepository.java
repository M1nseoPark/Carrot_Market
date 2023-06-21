package com.karrot.repository;

import com.karrot.dto.ChatRoomDto;
import com.karrot.entity.ChatRoom;
import com.karrot.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {

    List<ChatRoomDto> getChatRoomList(Long roomId);

    List<ChatRoomDto> getChatRoomId(Long itemId, Member seller);
}
