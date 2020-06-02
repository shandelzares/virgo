package com.virgo.exam.dto;

import com.virgo.exam.model.Question;
import lombok.Data;

import java.util.List;

@Data
public class ExamSaveParam {
    private String examId;
    private Status status;
    private List<Answer> answers;

    private Long recordId;//当前考试记录id

    public static enum Status {
        /**
         * 开始考试
         */
        START,
        /**
         * 继续考试，中途暂定，继续开始考试使用
         */
        CONTINUE,
        /**
         * 考试中
         */
        EXAMIING,
        /**
         * 提交考试
         */
        SUBMIT
    }

    @Data
    public static class Answer {
        private Long questionId;
        private Question.Type type;
        private String answer;
    }
}
