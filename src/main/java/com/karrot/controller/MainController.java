package com.karrot.controller;

import com.karrot.dto.ItemSearchDto;
import com.karrot.dto.MainItemDto;
import com.karrot.service.ItemService;
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

    @GetMapping(value = "/main")
    public String main(ItemSearchDto itemSearchDto, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<MainItemDto> items = itemService.getMainItemList(itemSearchDto);
        model.addAttribute("member", userDetails.getUsername());
        model.addAttribute("items", items);
        model.addAttribute("itemSerchDto", itemSearchDto);
        return "main";
    }
}