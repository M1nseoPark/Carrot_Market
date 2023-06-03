package com.karrot.dto;

import com.karrot.constant.ItemSellStatus;
import com.karrot.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "제목은 필수 입력 값입니다")
    private String title;

    @NotBlank(message = "카테고리는 필수 입력 값입니다")
    private String cate;

    @NotBlank(message = "가격은 필수 입력 값입니다")
    private int price;

    @NotBlank(message = "본문은 필수 입력 값입니다")
    private String comment;

    private ItemSellStatus itemSellStatus;

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();   // 상품 저장 후 수정할 때 상품 이미지 정보를 저장하는 리스트

    private List<Long> itemImgIds = new ArrayList<>();   // 상품의 이미지 아이디를 저장하는 리스트

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }
}
