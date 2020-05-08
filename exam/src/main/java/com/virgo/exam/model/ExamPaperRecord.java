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
    private Long id;
    private String examPaperCode;
    private String personalExamPaperCode;
    private String memberId;

    private Boolean pass;           //是否通过考试
    private Integer examScore;   //考试获得最高分数

    @CreatedDate
    private LocalDateTime createTime;
    @Version
    private Long version;
    private String companyCode;
}
