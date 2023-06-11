package com.karrot.controller;

import com.karrot.dto.ItemSearchDto;
import com.karrot.dto.MainItemDto;
import com.karrot.dto.MemberDto;
import com.karrot.entity.Member;
import com.karrot.service.ItemService;
import com.karrot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;
    private final MemberService memberService;

    @GetMapping(value = "/main")
    public String main(ItemSearchDto itemSearchDto, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<MainItemDto> items = itemService.getMainItemList(itemSearchDto);
        String email = userDetails.getUsername();
        Member member = memberService.findMember(email);
        MemberDto memberDto = MemberDto.builder()
                .id(member.getId())
                .nick(member.getNick())
                .build();

        model.addAttribute("member", memberDto);
        model.addAttribute("items", items);
        return "main";
    }
}