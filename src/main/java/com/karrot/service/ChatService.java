package com.karrot.service;

import com.karrot.dto.ChatRoomDto;
import com.karrot.entity.ChatRoom;
import com.karrot.entity.Item;
import com.karrot.entity.Member;
import com.karrot.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public Long createRoom(Item item, Member seller) {
        ChatRoom chatRoom = new ChatRoom(seller, item);
        chatRoomRepository.save(chatRoom);

        return chatRoom.getId();
    }

    public ChatRoomDto findRoom(Long roomId) {
        List<ChatRoomDto> chatRoomList = chatRoomRepository.getChatRoomList(roomId);
        return chatRoomList.get(0);
    }

    public Long findRoomId(Long itemId, Member seller) {
        List<ChatRoomDto> chatRoomDtoList = chatRoomRepository.getChatRoomId(itemId, seller);

        if (chatRoomDtoList.size() > 0) {
            return chatRoomDtoList.get(0).getId();
        }

        return null;
    }
}
