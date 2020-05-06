package com.virgo.member.controller;

import com.virgo.common.response.ResultData;
import com.virgo.member.dto.LoginParam;
import com.virgo.member.service.LoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @ApiOperation(value = "登陆", notes = "登陆")
    @PostMapping("v1/login")
    public ResultData<?> register(@Valid @RequestBody LoginParam loginParam) {
        return ResultData.success(loginService.login(loginParam));
    }
}
