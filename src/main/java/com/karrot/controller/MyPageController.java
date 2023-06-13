package com.karrot.controller;

import com.karrot.dto.MainItemDto;
import com.karrot.dto.MemberDto;
import com.karrot.entity.Member;
import com.karrot.service.ItemLikeService;
import com.karrot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;
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

    @GetMapping(value = "/like")
    public String myLikePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Member member = memberService.findMember(userDetails.getUsername());
        List<MainItemDto> items = itemLikeService.getLikeItemList(member);
        model.addAttribute("items", items);
        return "mypage/likeList";
    }

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
