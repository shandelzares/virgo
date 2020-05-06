package com.virgo.member.controller;

import com.virgo.common.response.ResultData;
import com.virgo.member.dto.MemberInfoChangeParam;
import com.virgo.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class MemberController {

    @Resource
    private MemberService memberService;

    @ApiOperation(value = "通过memberId查询会员详情", notes = "通过memberId查询会员详情")
    @GetMapping("v1/member/{memberId}")
    public ResultData<?> findByMemberId(@PathVariable String memberId) {
        return ResultData.success(memberService.findByMemberId(memberId));
    }

    @ApiOperation(value = "修改会员信息", notes = "修改会员信息")
    @PostMapping("v1/member")
    public ResultData<?> updateInfo(@Valid @RequestBody MemberInfoChangeParam memberInfoChangeParam) {
        return ResultData.success(memberService.updateInfo(memberInfoChangeParam));
    }
}
