package com.karrot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {

    private String email;

    private String password;

    private String name;

    private String phone;

    private String nick;
}