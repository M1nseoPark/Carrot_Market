package com.karrot.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="like_item")
@Getter
@Setter
@ToString
public class LikeItem {

    @Id
    @Column(name="l_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="i_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="m_id")
    private Member member;

    @Builder
    public LikeItem(Member member, Item item) {
        this.member = member;
        this.item = item;
    }
}
