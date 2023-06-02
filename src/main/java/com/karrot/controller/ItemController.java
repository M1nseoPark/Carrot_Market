package com.karrot.controller;

import com.karrot.dto.ItemFormDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ItemController {

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }
}
