package com.karrot.service;

import com.karrot.dto.MainItemDto;
import com.karrot.entity.LikeItem;
import com.karrot.entity.Member;
import com.karrot.repository.ItemLikeRepository;
import com.karrot.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemLikeService {

    private final ItemRepository itemRepository;
    private final ItemLikeRepository itemLikeRepository;

    // 특정 사용자의 관심 상품 목록
    @Transactional(readOnly = true)
    public List<MainItemDto> getLikeItemList(Member member) {
        return itemRepository.getLikeItemList(member.getId());
    }

    @Transactional(readOnly = true)
    public List<MainItemDto> getLikeItemSellList(Member member) {
        return itemRepository.getLikeItemSellList(member.getId());
    }

    @Transactional(readOnly = true)
    public List<MainItemDto> getLikeItemSoldList(Member member) {
        return itemRepository.getLikeItemSoldList(member.getId());
    }

    @Transactional
    public LikeItem addLike(LikeItem likeItem) {
        return itemLikeRepository.save(likeItem);
    }

    @Transactional
    public void deleteLike(Long itemId) {
        itemLikeRepository.deleteByItemId(itemId);
    }
}
