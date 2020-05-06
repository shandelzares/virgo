package com.virgo.article.dto;

import com.virgo.article.model.Article;
import com.virgo.common.page.AbstractPageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleQueryParam extends AbstractPageRequest {
    private String title;
    private String subTitle;
    private Article.Type type;
    private String subType;

}
