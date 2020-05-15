package com.virgo.exam.controller;

import com.virgo.common.response.ResultData;
import com.virgo.exam.dto.ExamSaveParam;
import com.virgo.exam.service.ExamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class ExamController {
    @Resource
    private ExamService examService;


    @ApiOperation(value = "考试")
    @PostMapping("v1/exam")
    public ResultData<?> findPage(@Valid ExamSaveParam examSaveParam) {

        return ResultData.success(examService.exam(examSaveParam));
    }
}
