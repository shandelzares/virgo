package com.virgo.member.controller;

import com.virgo.common.response.ResultData;
import com.virgo.member.dto.RegisterParam;
import com.virgo.member.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(value = "注册")
public class RegisterController {

    @Resource
    private RegisterService registerService;

    @ApiOperation(value = "注册", notes = "注册")
    @PostMapping("v1/register")
    public ResultData<?> register(@Valid @RequestBody RegisterParam registerParam) {
        registerService.register(registerParam);
        return ResultData.success();
    }
}
