package com.karrot.service;

import com.karrot.entity.Member;
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
    public void updateMemberImg(Member member, MemberImg memberImg, MultipartFile memberImgFile) throws Exception {
        if(memberImg == null) {
            MemberImg newMemberImg = new MemberImg();
            String oriImgName = memberImgFile.getOriginalFilename();
            String imgName = "";
            String imgUrl = "";

            if (!StringUtils.isEmpty(oriImgName)) {
                imgName = fileService.uploadFile(memberImgLocation, oriImgName, memberImgFile.getBytes());
                imgUrl = "/images/members/" + imgName;
            }

            newMemberImg.updateMemberImg(oriImgName, imgName, imgUrl);
            member.setMemberImg(newMemberImg);
            memberImgRepository.save(newMemberImg);
        }
        else {
            MemberImg savedMemberImg = memberImgRepository.findById(memberImg.getId()).orElseThrow(EntityNotFoundException::new);

            // 기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedMemberImg.getImgName())) {
                fileService.deleteFile(memberImgLocation+"/"+savedMemberImg.getImgName());
            }

            String oriImgName = memberImgFile.getOriginalFilename();

            String imgName = fileService.uploadFile(memberImgLocation, oriImgName, memberImgFile.getBytes());
            String imgUrl = "/images/members/" + imgName;
            member.setMemberImg(memberImg);
            savedMemberImg.updateMemberImg(oriImgName, imgName, imgUrl);
        }
    }
}
