package com.virgo.member.service;

import com.virgo.common.AppConfig;
import com.virgo.common.IdGenerator;
import com.virgo.common.RequestHolder;
import com.virgo.common.exception.BusinessException;
import com.virgo.common.exception.ResultEnum;
import com.virgo.common.validate.ValidateUtils;
import com.virgo.member.dto.RegisterParam;
import com.virgo.member.model.InvitationCode;
import com.virgo.member.model.Member;
import com.virgo.member.model.Sms;
import com.virgo.member.repository.InvitationCodeRepository;
import com.virgo.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class RegisterService {
    @Resource
    private MemberRepository memberRepository;
    @Resource
    private ValidateUtils validateUtils;
    @Resource
    private InvitationCodeRepository invitationCodeRepository;
    @Resource
    private SmsService smsService;
    @Resource
    private AppConfig appConfig;
    @Resource
    private PasswordEncoder passwordEncoder;

    public void register(RegisterParam registerParam) {
        log.info("用户注册 {}", registerParam);
        LocalDateTime now = LocalDateTime.now();
        switch (registerParam.getType()) {
            case PHONE_INVITATION_CODE:
                log.info("PHONE_INVITATION_CODE 注册方式");
                validateUtils.validate(registerParam, RegisterParam.PhoneInvitationCodeGroup.class);
                final InvitationCode invitationCode = findAndValidInvitationCode(registerParam, now);
                validSmsCode(registerParam);
                final Member member = createMember(registerParam, invitationCode);
                memberRepository.save(member);
                break;
            case PHONE:
                break;
            default:
                throw new BusinessException(ResultEnum.PARAM_ERROR);
        }
    }

    private Member createMember(RegisterParam registerParam, InvitationCode invitationCode) {
        Member member = new Member();
        member.setPhone(registerParam.getPhone());
        member.setCompanyCode(RequestHolder.getCompanyCode());
        member.setStatus(Member.Status.REGULAR);
        member.setInvitationCode(invitationCode.getCode());
        member.setType(Member.Type.MEMBER);
        member.setAvatarUrl(appConfig.getMember().getAvatarUrl());
        member.setNickName(appConfig.getMember().getNickPrefix() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ThreadLocalRandom.current().nextInt(1000, 9000));
        member.setMemberId(appConfig.getMember().getCodePrefix() + IdGenerator.nextMemberId());
        member.setPassword(passwordEncoder.encode(StringUtils.isEmpty(registerParam.getPassword()) ? registerParam.getPhone().substring(5) : registerParam.getPassword()));
        return member;
    }

    private void validSmsCode(RegisterParam registerParam) {
        Sms sms = smsService.findAvailable(registerParam.getPhone(), RequestHolder.getCompanyCode())
                .orElseThrow(() -> new BusinessException(ResultEnum.SMS_INCORRECT));
        if (!Objects.equals(sms.getCode(), registerParam.getSmsCode()))
            throw new BusinessException(ResultEnum.SMS_INCORRECT); //短信验证码不正确
    }

    private InvitationCode findAndValidInvitationCode(RegisterParam registerParam, LocalDateTime now) {
        InvitationCode invitationCode = invitationCodeRepository.findByCodeAndCompanyCode(registerParam.getInvitationCode(), RequestHolder.getCompanyCode())
                .orElseThrow(() -> new BusinessException(ResultEnum.INVITATION_CODE_NOT_FOUND));
        if (invitationCode.getMaxCount() <= invitationCode.getUsedCount())
            throw new BusinessException(ResultEnum.INVITATION_CODE_USED);//邀请码次数用完
        if (invitationCode.getStartTime().isAfter(now))
            throw new BusinessException(ResultEnum.INVITATION_CODE_UN_STARTED);//邀请码还未开始
        if (invitationCode.getEndTime().isBefore(now))
            throw new BusinessException(ResultEnum.INVITATION_CODE_FINISHED);//邀请码已经过期
        return invitationCode;
    }
}
