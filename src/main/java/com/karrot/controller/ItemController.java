package com.karrot.controller;

import com.karrot.dto.ItemFormDto;
import com.karrot.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }

    // 상품을 등록하는 url 설정
    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model) {
//                          , @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {

        // 상품 등록 시 필수 값이 없다면 다시 상품 등록 페이지로 전환함
        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }

//        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
//            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다.");
//            return "item/itemForm";
//        }

        // 상품 저장 로직 호출 -> 매개변수로 상품 정보와 상품 이미지 정보를 담고 있는 itemImgFileList를 넘겨줌
        try {
//            itemService.saveItem(itemFormDto, itemImgFileList);
            itemService.saveItem(itemFormDto, null);
        } catch (Exception e) {
            log.info("exception", e);
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        // 상품이 정상적으로 등록되었다면 메인 페이지로 이동함
        return "redirect:/";
    }
}
