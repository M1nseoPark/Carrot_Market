package com.karrot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @GetMapping
    public String myPage(Model model) {
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
