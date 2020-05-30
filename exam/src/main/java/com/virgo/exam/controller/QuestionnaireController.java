package com.virgo.exam.controller;

import com.virgo.common.response.ResultData;
import com.virgo.exam.service.QuestionnaireService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class QuestionnaireController {
    @Resource
    private QuestionnaireService questionnaireService;

    @GetMapping("v1/questionnaire/{id}")
    public ResultData<?> findById(@PathVariable Long id) {
        return ResultData.success(questionnaireService.findById(id));
    }
}
