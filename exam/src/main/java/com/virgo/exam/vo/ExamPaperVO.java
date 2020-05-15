package com.virgo.exam.vo;

import com.virgo.exam.model.ExamPaper;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExamPaperVO {
    private Long examPaperId;
    private Long id;
    private String code;
    private String name;
    /**
     * 分类
     */
    private String category;
    private ExamPaper.Type type;
    private ExamPaper.Status status;
    /**
     * 级别（等级、年级等）
     */
    private String level;
    private Integer score;
    private Integer lastTestScore;  //上次考试分数
    private Integer passScore;      //及格线
    private Integer avgDifficult;   //平均难度
    private Integer examTime;       //考试时间 秒
    private LocalDateTime examStartTime; //考试开始时间
    private LocalDateTime examEndTime; //考试结束时间
    private Integer switchScreen;       //切屏次数

    private Boolean fullScreen;         //是否全屏作答
    private Integer difficult;      //设定难度
    private Integer maxExamCount;   //最多考试次数

    private Integer questionCount;   //最多考试次数
    private Integer questionScoreTotal;   //最多考试次数

    private Boolean questionDerangement;    //题目乱序
    private Boolean optionsDerangement;     //选项乱序

    private String tags;

    private List<QuestionVO> questions;

    private String creator;//创建人code
    private String revisor;//更新人code
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
