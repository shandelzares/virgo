package com.virgo.exam.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExamSubmitParam {
    private String examId;
    private List<Answer> answers;

    @Data
    public static class Answer {
        private String questionId;
        private Object content;
        private Integer score;
    }
}
