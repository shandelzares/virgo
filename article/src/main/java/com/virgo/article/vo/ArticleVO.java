package com.virgo.article.vo;

import com.virgo.article.model.Article;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleVO {
    private Long id;
    private String memberId;

    private Boolean deleted;
    private Article.Type type;
    private String subType;
    private Article.Status status;

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

    private String creator;
    private String revisor;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;

    private String companyCode;
}
