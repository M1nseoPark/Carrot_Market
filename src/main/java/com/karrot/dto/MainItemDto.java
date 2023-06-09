package com.karrot.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainItemDto {

    private Long id;

    private String title;

    private String detail;

    private String imgUrl;

    private Integer price;

    private Integer like;

    @QueryProjection
    public MainItemDto(Long id, String title, String detail, String imgUrl, Integer price, Integer like) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.imgUrl = imgUrl;
        this.price = price;
        this.like = like;
    }

}
