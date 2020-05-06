package com.virgo.member.controller;

import com.virgo.common.response.ResultData;
import com.virgo.member.service.SmsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SmsController {

    @Resource
    private SmsService smsService;

    @ApiOperation(value = "短信验证码发送", notes = "短信验证码发送")
    @PostMapping("/v1/sms/send/{phone}")
    public ResultData<?> sendVerificationCode(@PathVariable("phone") String phone) {
        smsService.sendVerificationCode(phone);
        return ResultData.success();
    }
}
