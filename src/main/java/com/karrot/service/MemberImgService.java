package com.karrot.service;

import com.karrot.entity.MemberImg;
import com.karrot.repository.MemberImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberImgService {

    @Value("${memberImgLocation}")
    private String memberImgLocation;

    private final FileService fileService;

    private final MemberImgRepository memberImgRepository;

    @Transactional
    public MemberImg saveMemberImg(MemberImg memberImg) {
        return memberImgRepository.save(memberImg);
    }

    public MemberImg findMemberImg(Long memberId) {
        return memberImgRepository.findByMemberId(memberId);
    }

    @Transactional
    public void updateMemberImg(Long memberImgId, MultipartFile memberImgFile) throws Exception {
        if(!memberImgFile.isEmpty()) {
            MemberImg savedMemberImg = memberImgRepository.findById(memberImgId).orElseThrow(EntityNotFoundException::new);

            // 기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedMemberImg.getImgName())) {
                fileService.deleteFile(memberImgLocation+"/"+savedMemberImg.getImgName());
            }

            String oriImgName = memberImgFile.getOriginalFilename();

            String imgName = fileService.uploadFile(memberImgLocation, oriImgName, memberImgFile.getBytes());
            String imgUrl = "/images/members/" + imgName;
            savedMemberImg.updateMemberImg(oriImgName, imgName, imgUrl);
        }
    }
}
