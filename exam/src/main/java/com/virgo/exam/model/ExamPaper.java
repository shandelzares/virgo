package com.virgo.exam.model;

import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("exam-paper")
public class ExamPaper {

    @Id
    private String id;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 分类
     */
    private String category;
    /**
     * 试卷类型
     */
    private Type type;
    /**
     * 分数
     */
    private Integer score;
    /**
     * 及格线
     */
    private Integer passScore;      //及格线
    /**
     * 平均难度
     */
    private Float avgDifficult;   //平均难度
    /**
     * 考试时长
     */
    private Integer examTime;       //考试时间 秒
    /**
     * 考试开始时间
     */
    private LocalDateTime examStartTime; //考试开始时间
    /**
     * 考试结束时间
     */
    private LocalDateTime examEndTime; //考试结束时间
    /**
     * 允许切屏次数
     */
    private Integer switchScreen;       //切屏次数
    /**
     * 是否全屏作答
     */
    private Boolean fullScreen;         //是否全屏作答
    /**
     * 考试难度 1-5
     */
    private Integer difficult;      //设定难度
    /**
     * 最多考试次数
     */
    private Integer maxExamCount;   //最多考试次数
    /**
     * 是否自动阅卷
     */
    private Boolean autoScoring;    //自动阅卷
    /**
     * 题目个数
     */
    private Integer questionCount;   //最多考试次数
    /**
     * 题目乱序
     */
    private Boolean questionDerangement;    //题目乱序
    /**
     * 选项乱序
     */
    private Boolean optionsDerangement;     //选项乱序
    /**
     * 状态
     */
    private Status status;

    @Transient
    private List<Question> questions;

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
         * 考试
         */
        EXAM,
        /**
         * 练习
         */
        EXERCISE,
        /**
         * 问卷
         */
        QUESTIONNAIRE,
    }
}
