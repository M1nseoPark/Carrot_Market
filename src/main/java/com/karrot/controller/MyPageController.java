package com.karrot.controller;

import com.karrot.constant.ItemSellStatus;
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
import org.springframework.web.bind.annotation.*;

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

    // 관심목록 페이지 이동 (판매중)
    @GetMapping(value = "/like/sell")
    public String myLikePageSell(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Member member = memberService.findMember(userDetails.getUsername());
        List<MainItemDto> items = itemLikeService.getLikeItemSellList(member);
        model.addAttribute("items", items);
        return "mypage/likeList";
    }

    // 관심목록 페이지 이동 (거래완료)
    @GetMapping(value = "/like/sold")
    public String myLikePageSold(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Member member = memberService.findMember(userDetails.getUsername());
        List<MainItemDto> items = itemLikeService.getLikeItemSoldList(member);
        model.addAttribute("items", items);
        return "mypage/likeList";
    }

    // 판매내역 페이지 이동
    @GetMapping(value = "/sale")
    public String mySalePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Member member = memberService.findMember(userDetails.getUsername());
        List<MainItemDto> items = itemService.getSellerItemList(member.getId());
        model.addAttribute("items", items);
        return "mypage/saleList";
    }

    // 판매내역 페이지에서 판매상태 변경
    @GetMapping(value = "/sale/{itemId}/{status}")
    @ResponseBody
    public String mySaleStatus(@PathVariable("itemId") Long itemId, @PathVariable("status") ItemSellStatus status) {
        try {
            Item item = itemService.findItem(itemId);
            itemService.changeItemStatus(item, status);
        } catch (Exception e) {
        }
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
