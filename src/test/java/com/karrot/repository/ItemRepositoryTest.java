package com.karrot.repository;

import com.karrot.constant.ItemSellStatus;
import com.karrot.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setTitle("테스트 상품 판매합니다");
        item.setCate("디지털기기");
        item.setPrice(10000);
        item.setContent("테스트 상품 판매합니다.");
        item.setStatus(ItemSellStatus.SELL);
        item.setTime(LocalDateTime.now());
        item.setLike(0);
        item.setMember("홍길동");
    }

}