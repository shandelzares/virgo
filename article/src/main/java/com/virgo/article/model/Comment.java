package com.virgo.article.model;


import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Long parentId;
    private Boolean deleted;
    private String targetId;        //被评论id
    private Integer likeCount;      //喜欢、点赞次数
    private Integer dislikeCount;   //不喜欢、踩次数
    private Status status;

    @CreatedBy
    private String creator;
    @LastModifiedBy
    private String revisor;
    private LocalDateTime updateTime;
    @CreatedDate
    private LocalDateTime createTime;
    @Version
    private Long version;

    private String companyCode;

    public enum Status {
        /**
         * 待审
         */
        AUDITING,
        /**
         * 审核通过
         */
        AUDITED,
        /**
         * 审核不通过
         */
        UN_AUDITED
    }
}
