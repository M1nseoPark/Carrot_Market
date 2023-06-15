package com.karrot.controller;

import com.karrot.constant.ItemSellStatus;
import com.karrot.dto.ItemFormDto;
import com.karrot.dto.MainItemDto;
import com.karrot.dto.MemberDto;
import com.karrot.entity.Item;
import com.karrot.entity.Member;
import com.karrot.service.ItemLikeService;
import com.karrot.service.ItemService;
import com.karrot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final ItemLikeService itemLikeService;

    @GetMapping
    public String myPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        Member member = memberService.findMember(email);
        MemberDto memberDto = MemberDto.builder()
                .id(member.getId())
                .nick(member.getNick())
                .build();

        model.addAttribute("member", memberDto);
        return "mypage";
    }

    // 관심목록 페이지 이동 (전체)
    @GetMapping(value = "/like")
    public String myLikePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Member member = memberService.findMember(userDetails.getUsername());
        List<MainItemDto> items = itemLikeService.getLikeItemList(member);
        model.addAttribute("items", items);
        return "mypage/likeList";
    }

    // 관심목록 페이지 이동 (판매중, 거래완료)
    @GetMapping(value = "/like/{status}")
    public String myLikePageSell(@AuthenticationPrincipal UserDetails userDetails, Model model, @PathVariable("status") ItemSellStatus status) {
        Member member = memberService.findMember(userDetails.getUsername());

        if (status == ItemSellStatus.SELL) {
            List<MainItemDto> items = itemLikeService.getLikeItemSellList(member);
            model.addAttribute("items", items);
        }
        else {
            List<MainItemDto> items = itemLikeService.getLikeItemSoldList(member);
            model.addAttribute("items", items);
        }

        return "mypage/likeList";
    }

    // 판매내역 페이지 이동
    @GetMapping(value = "/sale/{status}")
    public String mySalePage(@AuthenticationPrincipal UserDetails userDetails, Model model, @PathVariable("status") ItemSellStatus status) {
        Member member = memberService.findMember(userDetails.getUsername());

        if (status == ItemSellStatus.SELL) {
            List<MainItemDto> items = itemService.getSellerItemListSell(member.getId());
            model.addAttribute("items", items);
        }
        else {
            List<MainItemDto> items = itemService.getSellerItemListSold(member.getId());
            model.addAttribute("items", items);
        }

        return "mypage/saleList";
    }

    // 판매내역 페이지에서 상품 수정
    @GetMapping(value = "/sale/edit/{itemId}")
    public String mySalePageEdit(@PathVariable("itemId") Long itemId, Model model) {
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        List<MainItemDto> sellerList = itemService.getSellerItemList(itemFormDto.getMember().getId());

        model.addAttribute("item", itemFormDto);
        model.addAttribute("sellerNick", itemFormDto.getMember().getNick());
        return "mypage/saleEdit";
    }

    // 판매내역 페이지에서 상품 수정 (게시글 수정)
    @GetMapping(value = "/sale/edit/{itemId}/edit")
    public String mySalePageEditEdit(@PathVariable("itemId") Long itemId, Model model) {
        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }
        return "item/itemForm";
    }

    // 판매내역 페이지에서 상품 수정 (게시글 삭제)
    @GetMapping(value = "/sale/edit/{itemId}/delete")
    public String mySalePageEditDelete(@PathVariable("itemId") Long itemId, Model model) {
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
        List<MainItemDto> sellerList = itemService.getSellerItemList(itemFormDto.getMember().getId());

        model.addAttribute("item", itemFormDto);
        model.addAttribute("sellerNick", itemFormDto.getMember().getNick());
        return "mypage/saleEdit";
    }

    // 판매내역 페이지에서 판매상태 변경
    @GetMapping(value = "/sale/{itemId}/{status}")
    @ResponseBody
    public String mySaleStatus(@PathVariable("itemId") Long itemId, @PathVariable("status") ItemSellStatus status, Model model) {
        Item item = itemService.findItem(itemId);
        itemService.changeItemStatus(item, status);

        if (status == ItemSellStatus.RESERVE)
            model.addAttribute("message", "예약중으로 변경하였습니다");
        else
            model.addAttribute("message", "거래완료로 변경하였습니다");

        return "success";
    }

    // 프로필 수정 페이지 이동
    @GetMapping(value = "/edit")
    public String editPage() {
        return "mypage/editProfile";
    }

    @PostMapping(value = "/edit")
    public String editProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        Member member = memberService.findMember(email);

//        try {
//            memberService.saveItem(member);
//        } catch (Exception e) {
//            model.addAttribute("errorMessage", "프로필 수정 중 에러가 발생하였습니다.");
//            return "mypage/editProfile";
//        }

        return "redirect:/mypage";
    }
}
