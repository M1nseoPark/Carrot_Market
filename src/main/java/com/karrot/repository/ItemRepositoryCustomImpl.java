package com.karrot.repository;

import com.karrot.dto.ItemSearchDto;
import com.karrot.dto.MainItemDto;
import com.karrot.dto.QMainItemDto;
import com.karrot.entity.Item;
import com.karrot.entity.QItem;
import com.karrot.entity.QItemImg;
import com.karrot.entity.QLikeItem;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    // 동적으로 쿼리를 생성하기 위해서 JPAQueryFactory 클래스를 사용함
    private JPAQueryFactory queryFactory;

    // JPAQueryFactory의 생성자로 EntityManager 객체를 넣어줌
    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

//    @Override
//    public List<Item> findMyProducts(Long memberId) {
//        return queryFactory
//                .select(product)
//                .from(product)
//                .join(product.member, member)
//                .where(product.member.id.eq(memberId))
//                .fetch();
//    }

    @Override
    public List<MainItemDto> getMainItemList(ItemSearchDto itemSearchDto) {

        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        return queryFactory
                .select(
                        new QMainItemDto(
                                item.id,
                                item.title,
                                item.detail,
                                itemImg.imgUrl,
                                item.price,
                                item.like)
                )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repimgYn.eq("Y"))
                .orderBy(item.id.desc())
                .fetch();
    }

    @Override
    public List<MainItemDto> getLikeItemList(Long memberId) {

        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;
        QLikeItem likeItem = QLikeItem.likeItem;

        return queryFactory
                .select(
                        new QMainItemDto(
                                item.id,
                                item.title,
                                item.detail,
                                itemImg.imgUrl,
                                item.price,
                                item.like)
                )
                .from(itemImg)
                .where(itemImg.repimgYn.eq("Y"), item.id.in(
                        JPAExpressions
                                .select(likeItem.item.id)
                                .from(likeItem)
                                .where(likeItem.member.id.eq(memberId))
                ))
                .fetch();
    }
}

