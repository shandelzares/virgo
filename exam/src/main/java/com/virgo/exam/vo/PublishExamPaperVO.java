package com.virgo.exam.vo;

import com.virgo.exam.model.ExamPaper;
import com.virgo.exam.model.PublishExamPaper;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PublishExamPaperVO {
    private String id;
    /**
     * 对应试卷id
     */
    private String examPaperId;
    /**
     * 名称
     */
    private String name;
    /**
     * 分类
     */
    private String category;
    /**
     * 试卷类型
     */
    private ExamPaper.Type type;
    /**
     * 分数
     */
    private Integer score;
    /**
     * 及格线
     */
    private Integer passScore;      //及格线
    /**
     * 平均难度
     */
    private Integer avgDifficult;   //平均难度
    /**
     * 考试时长
     */
    private Integer examTime;       //考试时间 秒
    /**
     * 考试开始时间
     */
    private LocalDateTime examStartTime; //考试开始时间
    /**
     * 考试结束时间
     */
    private LocalDateTime examEndTime; //考试结束时间
    /**
     * 允许切屏次数
     */
    private Integer switchScreen;       //切屏次数
    /**
     * 是否全屏作答
     */
    private Boolean fullScreen;         //是否全屏作答
    /**
     * 考试难度 1-5
     */
    private Integer difficult;      //设定难度
    /**
     * 最多考试次数
     */
    private Integer maxExamCount;   //最多考试次数
    /**
     * 是否自动阅卷
     */
    private Boolean autoScoring;    //自动阅卷
    /**
     * 题目个数
     */
    private Integer questionCount;   //最多考试次数
    /**
     * 题目乱序
     */
    private Boolean questionDerangement;    //题目乱序
    /**
     * 选项乱序
     */
    private Boolean optionsDerangement;     //选项乱序

    private Integer examCount;      //考试次数
    private Boolean pass;           //是否通过考试
    private Integer highestScore;   //考试获得最高分数
    private Integer lowestScore;    //考试获得最高分数

    private PublishExamPaper.Status status;
    private LocalDateTime startTime;

    private List<QuestionVO> questions;

    @CreatedDate
    private LocalDateTime createTime;
    @Version
    private Long version;
    private String companyCode;

}
