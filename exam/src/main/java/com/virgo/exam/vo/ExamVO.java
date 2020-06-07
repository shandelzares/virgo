package com.virgo.exam.vo;

import lombok.Data;

@Data
public class ExamVO {
    private Boolean scoring;
    private Boolean pass;        //是否通过考试
    private Integer examScore;   //考试获得最高分数
}
