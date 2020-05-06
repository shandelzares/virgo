package com.virgo.article.model;

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
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberId;

    private Boolean deleted;
    private Type type;
    private String subType;
    private Status status;

    private String title;
    private String subTitle;
    private String pic;
    private String subPic;
    private String targetUrl;   //跳转连接, 广告等
    private String source;      //来源
    private String sourceUrl;   //来源路径
    private String info;
    private String author;
    private String content;

    private Integer price;          //价格 以分为单位
    private Integer collectCount;   //收藏次数
    private Integer shareCount;     //分享次数
    private Integer sellCount;      //购买次数
    private Integer viewCount;      //查看次数
    private Integer likeCount;      //喜欢、点赞次数
    private Integer dislikeCount;   //不喜欢、踩次数
    private Integer commentCount;   //评论次数
    private String attach;          //附件

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

    public enum Type {
        /**
         * 文章
         */
        ARTICLE,
        /**
         * 新闻
         */
        NEWS,
        /**
         * 广告
         */
        AD,
        /**
         * 提问
         */
        QUESTION,
    }
}
