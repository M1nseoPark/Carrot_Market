package com.karrot.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="chat_msg")
public class ChatMsg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long id;

    private String sender;

    private String msg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "r_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "i_id")
    private Item item;

    @Builder
    public ChatMsg(String sender, String msg, ChatRoom chatRoom, Item item) {
        this.sender = sender;
        this.msg = msg;
        this.chatRoom = chatRoom;
        this.item = item;
    }
}
