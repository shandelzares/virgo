package com.virgo.member.vo;

import com.virgo.member.model.Member;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MemberVO {
    private Long id;
    private String memberId;
    private String phone;
    private String username;
    private String nickName;
    private String name;
    private Member.Sex sex;
    private LocalDate birthday;
    private String password;
    private String idCard;
    private String invitationCode;
    private Member.Status status;
    private Boolean deleted;
    private String creator;
    private String revisor;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
    private String avatarUrl;//头像地址
    private String companyCode;
    private Member.Type type;
}
