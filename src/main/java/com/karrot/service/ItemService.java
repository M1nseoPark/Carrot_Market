package com.karrot.service;

import com.karrot.dto.ItemFormDto;
import com.karrot.dto.ItemSearchDto;
import com.karrot.dto.MainItemDto;
import com.karrot.entity.Item;
import com.karrot.entity.ItemImg;
import com.karrot.repository.ItemImgRepository;
import com.karrot.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        // 상품 등록
        Item item = itemFormDto.createItem();   // 상품 등록 폼으로부터 입력 받은 데이터를 이용해 item 객체 생성
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

    // 메인 페이지에 보여줄 상품 데이터 조회
    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }
}
