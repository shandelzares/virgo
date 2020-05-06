package com.virgo.member;

import com.virgo.common.AppConfig;
import com.virgo.member.service.AliSmsClient;
import com.virgo.member.service.SmsClient;
import com.virgo.member.service.TencentSmsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfig {

    @Bean
    public SmsClient smsClient(AppConfig appConfig) throws Exception {
        switch (appConfig.getSms().getType()) {
            case ALIYUN:
                return new AliSmsClient();
            case TENCENT:
                return new TencentSmsClient();
            default:
                throw new Exception("短信发送client未找到");
        }
    }
}
