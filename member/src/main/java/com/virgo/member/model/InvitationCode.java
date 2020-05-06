package com.virgo.member.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class InvitationCode {
    @Id
    private Long id;
    private String code;
    private Integer maxCount;//邀请次数
    private Integer usedCount;//已使用次数
    private LocalDateTime startTime;//邀请码生效时间
    private LocalDateTime endTime;//失效时间
    private Boolean enable;
    private Boolean locked;
    private String owner;//拥有者
    @CreatedBy
    private String creator;//创建人code
    @LastModifiedBy
    private String revisor;//更新人code
    private LocalDateTime updateTime;
    @CreatedDate
    private LocalDateTime createTime;
    @Version
    private Long version;
    private String companyCode;
}
