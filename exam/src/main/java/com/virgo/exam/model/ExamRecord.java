package com.virgo.exam.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("exam-record")
public class ExamRecord {
    @Id
    private String id;
    private String examPaperId;
    private String publishExamPaperId;
    private Long userId;          //用户主键

    private Boolean pass;        //是否通过考试
    private Integer examScore;   //考试获得最高分数
    private LocalDateTime startTime;
    private String answer;
    private Boolean scoring;

    @CreatedDate
    private LocalDateTime createTime;
    @Version
    private Long version;
    private String companyCode;
}
