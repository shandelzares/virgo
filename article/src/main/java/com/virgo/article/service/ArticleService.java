package com.virgo.article.service;

import com.virgo.article.dto.ArticleQueryParam;
import com.virgo.article.model.Article;
import com.virgo.article.repository.ArticleRepository;
import com.virgo.article.vo.ArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ArticleService {
    @Resource
    private ArticleRepository articleRepository;

    public ArticleVO findById(Long id) {
        return articleRepository.findById(id).map(article -> {
            ArticleVO vo = new ArticleVO();
            BeanUtils.copyProperties(article, vo);
            return vo;
        }).orElse(null);
    }

    public Page<ArticleVO> findPage(ArticleQueryParam articleQueryParam) {
        return articleRepository.findAll((Specification<Article>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(articleQueryParam.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get("title"), articleQueryParam.getTitle() + "%"));
            }
            if (!StringUtils.isEmpty(articleQueryParam.getSubTitle())) {
                predicates.add(criteriaBuilder.like(root.get("subTitle"), articleQueryParam.getSubTitle() + "%"));
            }

            query.where(predicates.toArray(new Predicate[0])).orderBy();
            return query.getRestriction();
        }, articleQueryParam.pageable()).map(article -> {
            ArticleVO vo = new ArticleVO();
            BeanUtils.copyProperties(article, vo);
            return vo;
        });
    }
}
