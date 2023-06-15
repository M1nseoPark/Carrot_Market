package com.karrot.entity;

import com.karrot.dto.MemberFormDto;
import com.karrot.dto.MemberUpdateDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member {

    @Id
    @Column(name="m_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="m_img_id")
    private MemberImg memberImg;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String phone;

    @Column(unique = true)
    private String nick;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setEmail(memberFormDto.getEmail());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setName(memberFormDto.getName());
        member.setPhone(memberFormDto.getPhone());
        member.setNick(memberFormDto.getNick());
        return member;
    }

    public Member updateNick(String newNick) {
        this.nick = newNick;
        return this;
    }
}
