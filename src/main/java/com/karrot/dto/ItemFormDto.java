package com.karrot.dto;

import com.karrot.constant.ItemSellStatus;
import com.karrot.entity.Item;
import com.karrot.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "제목은 필수 입력 값입니다")
    private String title;

    @NotBlank(message = "카테고리는 필수 입력 값입니다")
    private String cate;

    @NotNull(message = "가격은 필수 입력 값입니다")
    private int price;

    @NotBlank(message = "본문은 필수 입력 값입니다")
    private String detail;

    private ItemSellStatus status;
    
    private LocalDateTime time;

    private int like;

    private Member member;

    // 상품 저장 후 수정할 때 상품 이미지 정보를 저장하는 리스트
    private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

    // 상품의 이미지 아이디를 저장하는 리스트
    private List<Long> itemImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    // modelMapper를 이용하여 엔티티 객체와 DTO 객체 간의 데이터를 복사하여 복사한 객체를 반환해주는 메소드
    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }
}
