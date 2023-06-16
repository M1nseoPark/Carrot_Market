package com.karrot.repository;

import com.karrot.entity.LikeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemLikeRepository extends JpaRepository<LikeItem, Long> {

    void deleteByItemId(Long itemId);
}
