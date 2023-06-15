package com.karrot.service;

import com.karrot.dto.MemberDto;
import com.karrot.dto.MemberUpdateDto;
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

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberImgService memberImgService;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

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

    @Transactional
    public Member updateNick(Member member, String newNick) {
        return member.updateNick(newNick);
    }

//    public Long updateMember(MemberUpdateDto memberUpdateDto, MultipartFile memberImgFile) throws Exception {
//        Member member = memberRepository.findById(memberUpdateDto.getId()).orElseThrow(EntityNotFoundException::new);
//        member.updateMember(memberUpdateDto);
//
//        Long memberImgId = memberUpdateDto.getMemberImgId();
//
//        // 이미지 등록
//        memberImgService.updateMemberImg(memberImgId, memberImgFile);
//
//        return member.getId();
//    }
}