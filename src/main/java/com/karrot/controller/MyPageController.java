package com.karrot.controller;

import com.karrot.constant.ItemSellStatus;
import com.karrot.dto.ItemFormDto;
import com.karrot.dto.MainItemDto;
import com.karrot.dto.MemberDto;
import com.karrot.dto.MemberUpdateDto;
import com.karrot.entity.Item;
import com.karrot.entity.ItemImg;
import com.karrot.entity.Member;
import com.karrot.entity.MemberImg;
import com.karrot.service.*;
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
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final ItemImgService itemImgService;
    private final ItemLikeService itemLikeService;
    private final MemberImgService memberImgService;

    @GetMapping
    public String myPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        Member member = memberService.findMember(email);
        MemberDto memberDto = MemberDto.builder()
                .id(member.getId())
                .nick(member.getNick())
                .imgUrl(member.getMemberImg())
                .build();

        model.addAttribute("member", memberDto);
        model.addAttribute("profile", member.getMemberImg());
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
        model.addAttribute("sellerImg", itemFormDto.getMember().getMemberImg());
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

    // 게시글 수정하기
    @PostMapping(value = "/sale/edit/{itemId}/edit")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model) {
        if(bindingResult.hasErrors()) {
            return "item/itemForm";
        }

        try {
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다");
            return "item/itemForm";
        }

        return "redirect:/mypage/sale/edit/{itemId}";
    }

    // 판매내역 페이지에서 상품 수정 (게시글 삭제)
    @GetMapping(value = "/sale/edit/{itemId}/delete")
    public String mySalePageEditDelete(@PathVariable("itemId") Long itemId) {
        Item item = itemService.findItem(itemId);

        itemLikeService.deleteLike(itemId);
        itemImgService.deleteItemImg(itemId);
        itemService.deleteItem(itemId);

        return "mypage/saleList";
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
    public String editPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        String email = userDetails.getUsername();
        Member member = memberService.findMember(email);

        MemberUpdateDto memberUpdateDto = MemberUpdateDto.builder()
                        .nick(member.getNick()).build();

        model.addAttribute("memberUpdateDto", memberUpdateDto);
        model.addAttribute("profile", member.getMemberImg());
        return "mypage/editProfile";
    }

    // 프로필 수정
    @PostMapping(value = "/edit")
    public String editProfile(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute MemberUpdateDto memberUpdateDto,
                              Model model) throws Exception {

        String email = userDetails.getUsername();
        Member member = memberService.findMember(email);

        if (!memberUpdateDto.getMemberImg().isEmpty()) {
            memberImgService.updateMemberImg(member, member.getMemberImg(), memberUpdateDto.getMemberImg());
        }

        try {
            memberService.updateNick(member, memberUpdateDto.getNick());
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "mypage/edit";
        }

        return "redirect:/mypage";
    }
}
