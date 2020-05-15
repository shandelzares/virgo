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
public class ExamPaperQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;

    private Long questionId;
    @ManyToOne
    private ExamPaper examPaper;
    /**
     * 分类
     */
    private String category;
    private Question.Type type;
    /**
     * 级别（等级、年级等）
     */
    private String level;
    private Integer score;
    private Integer difficult; //难度 1-10
    private String title;
    private String content;
    /**
     * json格式
     *{
     * "prefix": "A",
     * "content": "A选项",
     * "score": 1
     * }
     */
    private String answer;
    private String correctAnswer;
    /**
     *
     */
    private String tags;
    /**
     * 解析
     */
    private String analysis;
    @CreatedBy
    private String creator;//创建人code
    @LastModifiedBy
    private String revisor;//更新人code
    private LocalDateTime updateTime;
    @CreatedDate
    private LocalDateTime createTime;
    @Version
    private Long version;
    private String companyCode;

}
