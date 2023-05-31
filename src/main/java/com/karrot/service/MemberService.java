package com.karrot.service;

import com.karrot.entity.Member;
import com.karrot.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
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
}