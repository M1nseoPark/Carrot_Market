package com.karrot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="member_img")
@Getter
@Setter
public class MemberImg {

    @Id
    @Column(name="m_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;   // 이미지 파일명

    private String oriImgName;   // 원본 이미지 파일명

    private String imgUrl;   // 이미지 조회 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="m_id")
    private Member member;

    public void updateMemberImg(String imgName, String oriImgName, String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }
}
