package com.karrot.service;

import com.karrot.dto.ChatMsgDto;
import com.karrot.dto.ChatRoomDto;
import com.karrot.entity.ChatMsg;
import com.karrot.entity.ChatRoom;
import com.karrot.entity.Item;
import com.karrot.entity.Member;
import com.karrot.repository.ChatMsgRepository;
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
    private final ChatMsgRepository chatMsgRepository;

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

    @Transactional
    public void saveChat(ChatMsgDto chatMsgDto) {
        List<ChatRoomDto> chatRooms = chatRoomRepository.getChatRoomList(chatMsgDto.getRoomId());

        ChatMsg chatMsg = chatMsgDto.createChat(chatMsgDto.getSender(), chatMsgDto.getMsg(), chatRooms.get(0));
        chatMsgRepository.save(chatMsg);
    }
}
