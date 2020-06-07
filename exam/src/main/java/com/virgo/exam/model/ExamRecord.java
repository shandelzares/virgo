package com.virgo.exam.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("exam-record")
public class ExamRecord {
    @Id
    private String id;
    private String examPaperId;
    private String publishExamPaperId;
    private String memberId;          //用户主键

    private Boolean pass;        //是否通过考试
    private Integer examScore;   //考试获得最高分数
    private LocalDateTime startTime;
    private String answer;
    private Boolean scoring;
    private List<Question> questions;

    @CreatedDate
    private LocalDateTime createTime;
    @Version
    private Long version;
    private String companyCode;

    @Data
    public static class Question {
        /**
         * 题目id
         */
        private String questionId;
        /**
         * 试卷id
         */
        private String examPaperId;
        /**
         * 编码
         */
        private String code;
        /**
         * 分类
         */
        private String category;
        /**
         * 题目类型
         */
        private com.virgo.exam.model.Question.Type type;
        /**
         * 分数
         */
        private Integer score;
        /**
         * 获取的分数
         */
        private Integer obtainScore;
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
        private List<com.virgo.exam.model.Question.Answer> answer;

        private List<com.virgo.exam.model.Question.Answer> userAnswer;
        /**
         * 简答题打分标准
         */
        private List<com.virgo.exam.model.Question.ShortAnswerAnalysis> shortAnswerAnalysis;

        /**
         * 解析
         */
        private String analysis;

    }
}
