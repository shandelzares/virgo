package com.virgo.exam.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

    public AnswerVO(Integer id) {
        this.id = id;
    }
}
