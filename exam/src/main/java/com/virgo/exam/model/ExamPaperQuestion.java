package com.virgo.exam.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("exam-paper-question")
public class ExamPaperQuestion {
    @Id
    private String id;
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
    private Question.Type type;
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
    private List<Question.Answer> answer;
    /**
     * 简答题打分标准
     */
    private List<Question.ShortAnswerAnalysis> shortAnswerAnalysis;

    /**
     * 解析
     */
    private String analysis;

    private Question.RandomConfig randomConfig;


    @Version
    private Long version;

}
