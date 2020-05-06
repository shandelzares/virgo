package com.virgo.member.service;

import com.virgo.common.AppConfig;
import com.virgo.common.RequestHolder;
import com.virgo.common.exception.BusinessException;
import com.virgo.common.exception.ResultEnum;
import com.virgo.member.model.Sms;
import com.virgo.member.repository.SmsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SmsService {

    @Resource
    private SmsClient smsClient;

    @Resource
    private SmsRepository smsRepository;

    @Resource
    private AppConfig appConfig;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void sendVerificationCode(String phone) {
        log.info("短信发送 {}", phone);
        String v = stringRedisTemplate.opsForValue().get(appConfig.getSms().getLimitPrefix() + phone);
        if (v != null) {
            log.info("短信发送过于频繁 {}", phone);
            throw new BusinessException(ResultEnum.SMS_SEND_FREQUENTLY);
        }

        Sms sms = new Sms();
        sms.setCode(smsClient.send(phone));
        sms.setExpirationTime(LocalDateTime.now().plusSeconds(appConfig.getSms().getExpirationTime()));
        sms.setCellphone(phone);
        sms.setCompanyCode(RequestHolder.getCompanyCode());
        smsRepository.save(sms);
        stringRedisTemplate.opsForValue().set(appConfig.getSms().getLimitPrefix() + phone, "", appConfig.getSms().getLimitTime(), TimeUnit.SECONDS); //60s只能发送一次短信
    }

    public Optional<Sms> findAvailable(String phone, String companyCode) {
        return smsRepository.findFirstByCellphoneAndCompanyCodeAndExpirationTimeAfter(phone, companyCode, LocalDateTime.now());
    }


    public void validSmsCode(String phone, Integer smsCode, String companyCode) {
        Sms sms = findAvailable(phone, companyCode == null ? RequestHolder.getCompanyCode() : companyCode)
                .orElseThrow(() -> new BusinessException(ResultEnum.SMS_INCORRECT));
        if (!Objects.equals(sms.getCode(), smsCode))
            throw new BusinessException(ResultEnum.SMS_INCORRECT); //短信验证码不正确
    }
}

