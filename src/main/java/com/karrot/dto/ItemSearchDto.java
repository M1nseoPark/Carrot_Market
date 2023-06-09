package com.karrot.dto;

import com.karrot.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {

    // 상품의 판매 상태
    private ItemSellStatus searchSellStatus;

    // 상품명
    private String searchQuery = "";
}
