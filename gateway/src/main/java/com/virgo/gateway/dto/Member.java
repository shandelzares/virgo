package com.virgo.gateway.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Member {
    private String token;
    private String username;
    private String memberId;
    private Long id;
    private LocalDateTime loginTime;
    private LocalDateTime updateTime;
}
