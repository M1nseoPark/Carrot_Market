package com.karrot.repository;

import com.karrot.dto.ItemSearchDto;
import com.karrot.dto.MainItemDto;
import com.karrot.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

//    List<Item> findByMemberId(Long memberId);

    // 메인 페이지에 보여줄 상품 리스트 가져오기
    List<MainItemDto> getMainItemList(ItemSearchDto itemSearchDto);
}
