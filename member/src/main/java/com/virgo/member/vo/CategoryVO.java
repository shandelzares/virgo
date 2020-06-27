package com.virgo.member.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CategoryVO {
    private Long id;
    private String code;
    private String name;
    private Long parentId;
    /**
     * 分类类型
     * 考试分类、等
     */
    private String type;
    private Boolean enabled;
    private Integer sort;
    private String creator;
    private String revisor;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
    private List<CategoryVO> children;

}
