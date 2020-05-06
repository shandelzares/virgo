package com.virgo.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private Redis redis = new Redis();
    private Sms sms = new Sms();
    private Member member = new Member();


    @Data
    public static class Redis {
        private String prefix = "";
    }

    @Data
    public static class Member {
        private String avatarUrl = "";
        private String nickPrefix = "demo";
        private String codePrefix = "demo";
        private int tokenExpire = 30 * 24 * 60 * 60;
    }

    @Data
    public static class Sms {
        private SmsClientType type = SmsClientType.ALIYUN;
        private int limitTime = 60;
        private String limitPrefix = "limit:sms:send:";
        private int expirationTime = 5 * 60;
    }

    public enum SmsClientType {
        ALIYUN, TENCENT
    }
}
