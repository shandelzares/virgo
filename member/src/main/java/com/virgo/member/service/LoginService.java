package com.virgo.member.service;

import com.virgo.common.AppConfig;
import com.virgo.common.JsonUtils;
import com.virgo.common.RequestHolder;
import com.virgo.common.exception.BusinessException;
import com.virgo.common.exception.ResultEnum;
import com.virgo.common.validate.ValidateUtils;
import com.virgo.member.dto.LoginParam;
import com.virgo.member.dto.MemberRedisDTO;
import com.virgo.member.model.Member;
import com.virgo.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginService {
    @Resource
    private MemberRepository memberRepository;
    @Resource
    private ValidateUtils validateUtils;
    @Resource
    private SmsService smsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private AppConfig appConfig;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private static final String PREFIX = "user:token:";


    public String login(LoginParam loginParam) {
        log.info("用户登陆 {}", loginParam);
        LocalDateTime now = LocalDateTime.now();
        switch (loginParam.getType()) {
            case SMS:
                log.info("短信登陆方式");
                validateUtils.validate(loginParam, LoginParam.SmsGroup.class);
                Member member = memberRepository.findByPhoneAndDeletedIsFalse(loginParam.getPhone()).orElseThrow(() -> new BusinessException(ResultEnum.MEMBER_NOT_FOUND));
                if (Objects.equals(member.getStatus(), Member.Status.LOCKED))
                    throw new BusinessException(ResultEnum.ACCOUNT_LOCKED);

                smsService.validSmsCode(loginParam.getPhone(), loginParam.getSmsCode(), RequestHolder.getCompanyCode());
                String token = UUID.randomUUID().toString().replace("-", "");

                MemberRedisDTO redisDTO = new MemberRedisDTO();
                redisDTO.setMemberId(member.getMemberId());
                redisDTO.setUsername(member.getUsername());
                redisDTO.setLoginTime(now);
                redisDTO.setToken(token);
                redisDTO.setUpdateTime(now);
                stringRedisTemplate.opsForValue().set(PREFIX + token, JsonUtils.toJson(redisDTO), appConfig.getMember().getTokenExpire(), TimeUnit.SECONDS);
                return token;
            case WE_CHAT:
                log.info("微信登陆");

            default:
                throw new BusinessException(ResultEnum.PARAM_ERROR);
        }
    }
}
