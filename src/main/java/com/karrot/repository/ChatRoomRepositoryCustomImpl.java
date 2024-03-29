package com.karrot.repository;

import com.karrot.dto.ChatRoomDto;
import com.karrot.dto.QChatRoomDto;
import com.karrot.entity.Member;
import com.karrot.entity.QChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public ChatRoomRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ChatRoomDto> getChatRoomList(Long roomId) {

        QChatRoom chatRoom = QChatRoom.chatRoom;

        return queryFactory
                .select(
                        new QChatRoomDto(
                                chatRoom.id,
                                chatRoom.seller)
                )
                .from(chatRoom)
                .where(chatRoom.id.eq(roomId))
                .fetch();
    }

    @Override
    public List<ChatRoomDto> getChatRoomId(Long itemId, Member seller) {

        QChatRoom chatRoom = QChatRoom.chatRoom;

        return queryFactory
                .select(
                        new QChatRoomDto(
                                chatRoom.id,
                                chatRoom.seller
                        )
                )
                .from(chatRoom)
                .where(chatRoom.seller.eq(seller), chatRoom.item.id.eq(itemId))
                .fetch();
    }
}
