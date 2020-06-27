package com.virgo.member.controller;

import com.virgo.common.response.ResultData;
import com.virgo.member.service.CategoryService;
import com.virgo.member.vo.CategoryVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @ApiOperation(value = "分类查询")
    @GetMapping("v1/category")
    public ResultData<List<CategoryVO>> findAll() {
        return ResultData.success(categoryService.findAll());
    }
}
