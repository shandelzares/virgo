package com.virgo.exam.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class PersonalExamPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long examPaperId;
    private Long memberId;

    private Integer examCount;      //考试次数
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

    public static enum Status{
        START,
        END
    }
}