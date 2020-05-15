package com.virgo.exam.dto;

import com.virgo.common.page.AbstractPageRequest;
import com.virgo.exam.model.ExamPaper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExamPaperQueryParam extends AbstractPageRequest {
    private String code;
    private String name;
    /**
     * 分类
     */
    private String category;
    private List<ExamPaper.Type> type;
    private String title;

}
