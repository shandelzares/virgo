package com.virgo.exam.model;

import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("exam-question")
public class Question {
    @Id
    private String id;

    private String code;
    /**
     * 分类
     */
    private String category;
    /**
     * 题目类型
     */
    private Type type;
    /**
     * 分数
     */
    private Integer score;
    /**
     * 难度
     */
    private Integer difficult; //难度 1-5
    /**
     * 标题
     */
    private String title;
    /**
     * 题干
     */
    private String stem;
    /**
     * 答案
     */
    private List<Answer> answer;
    /**
     * 简答题打分标准
     */
    private List<ShortAnswerAnalysis> shortAnswerAnalysis;

    /**
     * 解析
     */
    private String analysis;
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


    @Data
    public static class Answer {
        /**
         * id
         */
        private Integer id;
        /**
         * 答案内容
         */
        private String content;
        /**
         * 分数
         */
        private Integer score;
        /**
         * 是否是正确答案
         */
        private Boolean isCorrect;
    }


    @Data
    public static class ShortAnswerAnalysis {
        /**
         * 关键字
         */
        private String keyWords;
        /**
         * 分数
         */
        private Double score;
    }

    public static enum Type {
        /**
         * 单选
         */
        SINGLE_SELECT,
        /**
         * 多选
         */
        MULTI_SELECT,
        /**
         * 判断
         */
        TRUE_FALSE,
        /**
         * 简答
         */
        SHORT_ANSWER,
        /**
         * 填空
         */
        COMPLETION,
        /**
         * 下拉选框
         */
        SELECT,
        /**
         * 分数
         */
        SCORE
    }
}