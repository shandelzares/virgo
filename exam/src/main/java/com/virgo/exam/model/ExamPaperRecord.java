package com.virgo.exam.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class ExamPaperRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long examPaperId;
    private Long personalExamPaperId;
    private Long userId;          //用户主键

    private Boolean pass;        //是否通过考试
    private Integer examScore;   //考试获得最高分数
    private LocalDateTime startTime;
    private String answer;

    @CreatedDate
    private LocalDateTime createTime;
    @Version
    private Long version;
    private String companyCode;
}
