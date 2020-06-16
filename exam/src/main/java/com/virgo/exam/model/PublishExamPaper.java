package com.virgo.exam.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("exam-publish")
public class PublishExamPaper {
    @Id
    private String id;
    private String examPaperId;
    private String memberId;
    private String userId;

    private Integer examCount;      //考试次数
    private Integer lastScore;      //最后一次考试分数
    private Boolean pass;           //是否通过考试
    private Integer highestScore;   //考试获得最高分数
    private Integer lowestScore;    //考试获得最高分数

    private Status status;

    private LocalDateTime startTime;

    @CreatedDate
    private LocalDateTime createTime;
    @Version
    private Long version;
    private String companyCode;

    public static enum Status {
        START,
        /**
         * 阅卷中
         */
        SCORING,
        /**
         * 考试结束
         */
        END
    }
}
