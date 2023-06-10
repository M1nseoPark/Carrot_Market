package com.karrot.service;

import com.karrot.entity.LikeItem;
import com.karrot.repository.ItemLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemLikeService {

    private final ItemLikeRepository itemLikeRepository;

    @Transactional
    public LikeItem addLike(LikeItem likeItem) {
        return itemLikeRepository.save(likeItem);
    }
}
