package com.karrot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="like_item")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LikeItem {

    @Id
    @Column(name="l_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long itemInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="i_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="m_id")
    private Member member;

    @Builder
    public LikeItem(Member member, Item item, Long itemInfo) {
        this.member = member;
        this.item = item;
        this.itemInfo = itemInfo;
    }
}
