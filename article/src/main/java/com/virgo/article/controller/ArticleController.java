package com.virgo.article.controller;

import com.virgo.article.dto.ArticleQueryParam;
import com.virgo.article.service.ArticleService;
import com.virgo.article.vo.ArticleVO;
import com.virgo.common.response.ResultData;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @ApiOperation(value = "文章详情", notes = "文章详情")
    @GetMapping("v1/article/{id}")
    public ResultData<ArticleVO> detail(@PathVariable Long id) {
        return ResultData.success(articleService.findById(id));
    }

    @ApiOperation(value = "文章列表", notes = "文章列表")
    @GetMapping("v1/article")
    public ResultData<Page<ArticleVO>> page(@Valid ArticleQueryParam articleQueryParam) {
        return ResultData.success(articleService.findPage(articleQueryParam));
    }

}
