package com.karrot.dto;

import com.karrot.entity.Member;
import com.karrot.entity.MemberImg;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateDto {

    private MultipartFile memberImg;
    private String nick;

    @Builder
    public MemberUpdateDto(MultipartFile memberImg, String nick) {
        this.memberImg = memberImg;
        this.nick = nick;
    }
}
