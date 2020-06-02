package com.virgo.exam.vo;

import lombok.Data;

@Data
public class AnswerVO {
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
