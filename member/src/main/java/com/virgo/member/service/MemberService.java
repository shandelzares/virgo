package com.virgo.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.virgo.common.exception.BusinessException;
import com.virgo.common.exception.ResultEnum;
import com.virgo.member.dto.MemberInfoChangeParam;
import com.virgo.member.repository.MemberRepository;
import com.virgo.member.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class MemberService {
    @Resource
    private MemberRepository memberRepository;

    public MemberVO findByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId).map(member -> {
            MemberVO vo = new MemberVO();
            BeanUtils.copyProperties(member, vo);
            return vo;
        }).orElse(null);
    }

    public MemberVO updateInfo(MemberInfoChangeParam memberInfoChangeParam) {
        return memberRepository.findByMemberId(memberInfoChangeParam.getMemberId())
                .map(member -> {
                    BeanUtil.copyProperties(memberInfoChangeParam, member, CopyOptions.create().ignoreNullValue().setIgnoreProperties("memberId"));
                    memberRepository.save(member);
                    MemberVO vo = new MemberVO();
                    BeanUtils.copyProperties(member, vo);
                    return vo;
                }).orElseThrow(() -> new BusinessException(ResultEnum.MEMBER_NOT_FOUND));
    }
}
