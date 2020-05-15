package com.virgo.exam.vo;

import com.virgo.exam.model.Question;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionVO {
    private Long id;
    private String code;
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
     * {
     * "prefix": "A",
     * "content": "A选项",
     * "score": 1
     * }
     */
    private List<Answer> answer;
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

    @Data
    public static class Answer{
        private String prefix;
        private String value;
    }
}
