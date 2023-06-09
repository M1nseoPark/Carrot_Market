package com.karrot.repository;

import com.karrot.constant.ItemSellStatus;
import com.karrot.dto.ItemSearchDto;
import com.karrot.dto.MainItemDto;
import com.karrot.dto.QMainItemDto;
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
                                item.like,
                                item.status,
                                item.member)
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
                                item.like,
                                item.status,
                                item.member)
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

    @Override
    public List<MainItemDto> getLikeItemSellList(Long memberId) {

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
                                item.like,
                                item.status,
                                item.member)
                )
                .from(itemImg)
                .where(itemImg.repimgYn.eq("Y"),
                        item.status.eq(ItemSellStatus.SELL).or(item.status.eq(ItemSellStatus.RESERVE)), item.id.in(
                        JPAExpressions
                                .select(likeItem.item.id)
                                .from(likeItem)
                                .where(likeItem.member.id.eq(memberId))
                ))
                .fetch();
    }

    @Override
    public List<MainItemDto> getLikeItemSoldList(Long memberId) {

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
                                item.like,
                                item.status,
                                item.member)
                )
                .from(itemImg)
                .where(itemImg.repimgYn.eq("Y"), item.status.eq(ItemSellStatus.SOLD_OUT), item.id.in(
                        JPAExpressions
                                .select(likeItem.item.id)
                                .from(likeItem)
                                .where(likeItem.member.id.eq(memberId))
                ))
                .fetch();
    }

    @Override
    public List<MainItemDto> getSellerItemList(Long ownerId) {

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
                                item.like,
                                item.status,
                                item.member)
                )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repimgYn.eq("Y"), item.member.id.eq(ownerId))
                .fetch();
    }

    @Override
    public List<MainItemDto> getSellerItemListSell(Long ownerId) {

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
                                item.like,
                                item.status,
                                item.member)
                )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repimgYn.eq("Y"), item.member.id.eq(ownerId),
                        item.status.eq(ItemSellStatus.SELL).or(item.status.eq(ItemSellStatus.RESERVE)))
                .fetch();
    }

    @Override
    public List<MainItemDto> getSellerItemListSold(Long ownerId) {

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
                                item.like,
                                item.status,
                                item.member)
                )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repimgYn.eq("Y"), item.member.id.eq(ownerId), item.status.eq(ItemSellStatus.SOLD_OUT))
                .fetch();
    }
}

