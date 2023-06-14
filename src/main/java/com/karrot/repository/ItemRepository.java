package com.karrot.repository;

import com.karrot.dto.ItemSearchDto;
import com.karrot.dto.MainItemDto;
import com.karrot.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    List<Item> findByMemberId(Long memberId);

    // 메인 페이지에 보여줄 상품 리스트 가져오기
    List<MainItemDto> getMainItemList(ItemSearchDto itemSearchDto);

    // 관심목록 페이지에 보여줄 상품 리스트 가져오기 (전체)
    List<MainItemDto> getLikeItemList(Long memberId);

    // 관심목록 페이지에 보여줄 상품 리스트 가져오기 (판매중)
    List<MainItemDto> getLikeItemSellList(Long memberId);

    // 관심목록 페이지에 보여줄 상품 리스트 가져오기 (거래완료)
    List<MainItemDto> getLikeItemSoldList(Long memberId);

    // 판매상품 페이지에 보여줄 상품 리스트 가져오기 (전체)
    List<MainItemDto> getSellerItemList(Long ownerId);

    // 판매상품 페이지에 보여줄 상품 리스트 가져오기 (판매중)
    List<MainItemDto> getSellerItemListSell(Long ownerId);

    // 판매상품 페이지에 보여줄 상품 리스트 가져오기 (거래완료)
    List<MainItemDto> getSellerItemListSold(Long ownerId);
}
