package com.karrot.repository;

import com.karrot.dto.ItemSearchDto;
import com.karrot.dto.MainItemDto;
import com.karrot.entity.Item;

import java.util.List;

public interface ItemRepositoryCustom {

//    List<Item> findMyItem(Long memberId);
//
//    List<Item> likeMyItem(Long memberId);

    // 메인 페이지에 보여줄 상품 리스트 가져오기
    List<MainItemDto> getMainItemList(ItemSearchDto itemSearchDto);
}