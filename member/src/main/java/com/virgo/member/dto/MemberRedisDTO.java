package com.virgo.member.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberRedisDTO {
    private String token;
    private String username;
    private String memberId;
    private Long id;
    private LocalDateTime loginTime;
    private LocalDateTime updateTime;
}
