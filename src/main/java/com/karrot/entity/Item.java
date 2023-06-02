package com.karrot.entity;

import com.karrot.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item {

    @Id
    @Column(name="i_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="i_title", nullable = false, length = 50)
    private String title;   // 게시글 제목

    @Column(name="i_cate", nullable = false)
    private String cate;   // 카테고리

    @Column(name="i_price", nullable = false)
    private int price;   // 가격

    @Column(name="i_content", nullable = false)
    private String content;   // 게시글 내용

    @Column(name="i_status")
    @Enumerated(EnumType.STRING)
    private ItemSellStatus status;   // 거래 상태

    @Column(name="i_time")
    private LocalDateTime time;   // 게시시간

    @Column(name="i_like")
    private int like;   // 관심(하트 숫자)

    @Column(name="i_member")
    private String member;   // 판매자 닉네임

}
