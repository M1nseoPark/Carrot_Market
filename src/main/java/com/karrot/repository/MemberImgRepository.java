package com.karrot.repository;

import com.karrot.entity.MemberImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberImgRepository extends JpaRepository<MemberImg, Long> {

    MemberImg findByMemberId(Long memberId);
}
