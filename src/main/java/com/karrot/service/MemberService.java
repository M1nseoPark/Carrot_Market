package com.karrot.service;

import com.karrot.dto.ItemFormDto;
import com.karrot.dto.MemberDto;
import com.karrot.entity.Item;
import com.karrot.entity.ItemImg;
import com.karrot.entity.Member;
import com.karrot.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

//    public Long saveItem(MemberDto memberDto, List<MultipartFile> itemImgFileList) throws Exception {
//
//        // 상품 등록
//        Member member = memberDto.createItem();   // 상품 등록 폼으로부터 입력 받은 데이터를 이용해 item 객체 생성
//        itemRepository.save(item);   // 상품 데이터 저장
//
//        // 이미지 등록
//        for (int i = 0; i < itemImgFileList.size(); i++) {
//            ItemImg itemImg = new ItemImg();
//            itemImg.setItem(item);
//            if (i == 0)   // 첫 번째 이미지일 경우 대표 상품 이미지 여부 값을 Y로 설정
//                itemImg.setRepimgYn("Y");
//            else
//                itemImg.setRepimgYn("N");
//            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));   // 상품의 이미지 정보를 저장
//        }
//
//        return item.getId();
//    }

    public Member findMember(String email) {
        return memberRepository.findByEmail(email);
    }

    private void validateDuplicateMember(Member member) {
        Member findEmail = memberRepository.findByEmail(member.getEmail());
        if (findEmail != null) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }

        Member findNick = memberRepository.findByNick(member.getNick());
        if (findNick != null) {
            throw new IllegalStateException("이미 사용중인 닉네임입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles("ADMIN")
                .build();
    }
}