package com.virgo.exam.controller;

import com.virgo.common.page.PageResult;
import com.virgo.common.response.ResultData;
import com.virgo.exam.dto.ExamPaperQueryParam;
import com.virgo.exam.service.ExamPaperService;
import com.virgo.exam.vo.ExamPaperVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class ExamPaperController {
    @Resource
    private ExamPaperService examPaperService;


    @ApiOperation(value = "试卷列表", notes = "试卷列表")
    @GetMapping("v1/exam/paper")
    public ResultData<PageResult<ExamPaperVO>> findPage(@Valid ExamPaperQueryParam questionQueryParam) {
        return ResultData.success(examPaperService.findPage(questionQueryParam));
    }

    @ApiOperation(value = "试卷列表", notes = "试卷列表")
    @GetMapping("v1/exam/paper/{id}")
    public ResultData<ExamPaperVO> findPage(@PathVariable Long id) {
        return ResultData.success(examPaperService.findById(id));
    }
}
