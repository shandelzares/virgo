package com.virgo.gateway;

import com.virgo.common.AppAutoConfiguration;
import com.virgo.common.AppConfig;
import com.virgo.common.JsonUtils;
import com.virgo.common.redis.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
@Import({JsonUtils.class, RedisConfig.class, AppAutoConfiguration.class})
public class GatewayBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(GatewayBootstrap.class, args);
    }
}
