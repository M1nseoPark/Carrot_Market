package com.karrot.repository;

import com.karrot.dto.ChatRoomDto;
import com.karrot.dto.QChatRoomDto;
import com.karrot.entity.ChatRoom;
import com.karrot.entity.Member;
import com.karrot.entity.QChatRoom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.karrot.entity.QItem.item;
import static com.karrot.entity.QMember.member;

public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom {

    private JPAQueryFactory queryFactory;

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
    public List<ChatRoom> getChatRoomId(Long itemId, Member seller) {

        QChatRoom chatRoom = QChatRoom.chatRoom;

        return queryFactory
                .selectFrom(chatRoom)
                .where(chatRoom.seller.eq(seller), chatRoom.item.id.eq(itemId))
                .fetch();
    }
}

