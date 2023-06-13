package com.karrot.controller;

import com.karrot.dto.ItemFormDto;
import com.karrot.dto.MainItemDto;
import com.karrot.entity.Item;
import com.karrot.entity.LikeItem;
import com.karrot.entity.Member;
import com.karrot.service.ItemLikeService;
import com.karrot.service.ItemService;
import com.karrot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final MemberService memberService;
    private final ItemLikeService itemLikeService;

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }

    // 상품을 등록하는 url 설정
    @PostMapping(value = "/admin/item/new")
    public String itemNew(@AuthenticationPrincipal UserDetails userDetails, @Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
        // 상품 등록 시 필수 값이 없다면 다시 상품 등록 페이지로 전환함
        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }

        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다.");
            return "item/itemForm";
        }

        // 상품 저장 로직 호출 -> 매개변수로 상품 정보와 상품 이미지 정보를 담고 있는 itemImgFileList를 넘겨줌
        try {
            Member member = memberService.findMember(userDetails.getUsername());
            itemService.saveItem(itemFormDto, itemImgFileList, member);
        } catch (Exception e) {
            log.info("exception", e);
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        // 상품이 정상적으로 등록되었다면 메인 페이지로 이동함
        return "main";
    }

    // 상품 상세정보 보기
    @GetMapping(value = "/item/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId) {
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        List<MainItemDto> sellerList = itemService.getSellerItemList(itemFormDto.getMember().getId());

        model.addAttribute("item", itemFormDto);
        model.addAttribute("sellerNick", itemFormDto.getMember().getNick());
        model.addAttribute("seller", sellerList);

        return "item/itemDtl";
    }

    // 판매상품 보기 페이지
    @GetMapping(value = "/item/{itemId}/{sellerId}")   // -> 링크 이렇게 해야 상세페이지랑 안겹침
    public String itemSeller(Model model, @PathVariable("sellerId") Long sellerId) {
        List<MainItemDto> sellerList = itemService.getSellerItemList(sellerId);
        model.addAttribute("items", sellerList);
        return "item/itemMember";
    }

    // 좋아요 반영하기
    @GetMapping(value = "item/{itemId}/addLike")
    @ResponseBody
    public String addLike(@PathVariable Long itemId, @AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberService.findMember(userDetails.getUsername());
        Item item = itemService.findItem(itemId);

        itemLikeService.addLike(new LikeItem(member, item));

        itemService.addLike(item);

        return "success";
    }
}
