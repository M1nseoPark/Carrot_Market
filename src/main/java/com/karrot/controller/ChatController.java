package com.karrot.controller;

import com.karrot.dto.ChatRoomDto;
import com.karrot.dto.ItemFormDto;
import com.karrot.entity.Item;
import com.karrot.entity.Member;
import com.karrot.service.ChatService;
import com.karrot.service.ItemService;
import com.karrot.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MemberService memberService;
    private final ChatService chatService;
    private final ItemService itemService;

    // 채팅방 만들기
    @PostMapping(value = "item/{itemId}/chat")
    public String createRoom(@PathVariable("itemId") Long itemId, @AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberService.findMember(userDetails.getUsername());
        Item item = itemService.findItem(itemId);
        log.info("Create Chat Room, senderNick: " + member.getNick());
        Long roomId = chatService.createRoom(item, member);

        return "redirect:/item"+itemId+"/chat/"+roomId;
    }

    // 채팅방 조회
    @GetMapping(value = "item/{itemId}/chat/{roomId}")
    public String chatGET(@PathVariable("roomId") Long roomId, @PathVariable("itemId") Long itemId, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        log.info("Get Chat Room, roomID: " + roomId);
        ChatRoomDto chatRoomDto = chatService.findRoom(roomId);
        Member member = memberService.findMember(userDetails.getUsername());
        ItemFormDto itemFormDto = itemService.getItemDtl(itemId);

        model.addAttribute("room", chatRoomDto);
        model.addAttribute("member", member);
        model.addAttribute("item", itemFormDto);
        return "item/chatForm";
    }
}
