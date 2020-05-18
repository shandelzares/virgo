package com.virgo.exam.model;

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
public class ExamPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    /**
     * 分类
     */
    private String category;
    private Type type;
    /**
     * 级别（等级、年级等）
     */
    private String level;
    private Integer score;
    private Integer lastTestScore;  //上次考试分数
    private Integer passScore;      //及格线
    private Integer avgDifficult;   //平均难度
    private Integer examTime;       //考试时间 秒
    private LocalDateTime examStartTime; //考试开始时间
    private LocalDateTime examEndTime; //考试结束时间
    private Integer switchScreen;       //切屏次数
    private Boolean fullScreen;         //是否全屏作答

    private Integer difficult;      //设定难度
    private Integer maxExamCount;   //最多考试次数
    private Boolean autoScoring;    //自动阅卷

    private Integer questionCount;   //最多考试次数
    private Integer questionScoreTotal;   //最多考试次数

    private Boolean questionDerangement;    //题目乱序
    private Boolean optionsDerangement;     //选项乱序

    private String tags;

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
    private Status status;

    public static enum Status {
        /**
         * 草稿
         */
        DRAFT,
        /**
         * 发布
         */
        PUBLISHED,
        /**
         * 删除
         */
        DELETED
    }

    public static enum Type {
        /**
         * 随机
         */
        RANDOM,
        /**
         * 固定题目
         */
        FIXED,
        /**
         * 混合
         */
        MIXTURE,
        /**
         * 简答
         */
        SHORT_ANSWER,
        /**
         * 填空
         */
        COMPLETION
    }
}
