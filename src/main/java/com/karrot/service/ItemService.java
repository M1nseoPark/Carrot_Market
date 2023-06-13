package com.karrot.service;

import com.karrot.constant.ItemSellStatus;
import com.karrot.dto.ItemFormDto;
import com.karrot.dto.ItemImgDto;
import com.karrot.dto.ItemSearchDto;
import com.karrot.dto.MainItemDto;
import com.karrot.entity.Item;
import com.karrot.entity.ItemImg;
import com.karrot.entity.Member;
import com.karrot.repository.ItemImgRepository;
import com.karrot.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList, Member member) throws Exception {

        // 상품 등록
        Item item = itemFormDto.createItem();   // 상품 등록 폼으로부터 입력 받은 데이터를 이용해 item 객체 생성
        item.setMember(member);
        item.setStatus(ItemSellStatus.SELL);
        item.setTime(LocalDateTime.now());
        itemRepository.save(item);   // 상품 데이터 저장

        // 이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if (i == 0)   // 첫 번째 이미지일 경우 대표 상품 이미지 여부 값을 Y로 설정
                itemImg.setRepimgYn("Y");
            else
                itemImg.setRepimgYn("N");
            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));   // 상품의 이미지 정보를 저장
        }

        return item.getId();
    }

    public Item findItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId) {
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);   // 등록순으로 가지고 오기 위해 상품 이미지 아이디 오름차순으로 가져옴
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    // 메인 페이지에 보여줄 상품 데이터 조회
    @Transactional(readOnly = true)
    public List<MainItemDto> getMainItemList(ItemSearchDto itemSearchDto) {
        return itemRepository.getMainItemList(itemSearchDto);
    }

    // 관심 개수 증가
    @Transactional
    public void addLike(Item item) {
        item.addLike();
    }
}
