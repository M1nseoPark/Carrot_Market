package com.karrot.controller;

import com.karrot.dto.MemberDto;
import com.karrot.entity.Member;
import com.karrot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;

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
    public String myLikePage(Model model) {
        return "mypage/likeList";
    }

    @GetMapping(value = "/edit")
    public String editProfile(Model model) {
        return "mypage/editProfile";
    }
}
