package com.virgo.common.redis;

import com.virgo.common.AppConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableConfigurationProperties(AppConfig.class)
public class RedisConfig {

    @Resource
    private AppConfig appConfig;

    @Bean(name = "redisTemplate")
    @ConditionalOnClass(RedisOperations.class)
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();

        redisTemplate.setConnectionFactory(connectionFactory);

        //key序列化方式;（不然会出现乱码;）,但是如果方法上有Long等非String类型的话，会报类型转换错误；
        //所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现ObjectRedisSerializer
        //或者JdkSerializationRedisSerializer序列化方式;
        RedisSerializer<String> redisSerializer = new StringRedisSerializer(StandardCharsets.UTF_8);//Long类型不可以会出现异常信息;
        RedisSerializer<String> keySerializer = new KeyPrefixStringRedisSerializer(StandardCharsets.UTF_8);//Long类型不可以会出现异常信息;

        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        return redisTemplate;
    }

    public class KeyPrefixStringRedisSerializer extends StringRedisSerializer {
        private final Charset charset;

        public KeyPrefixStringRedisSerializer() {
            this(StandardCharsets.UTF_8);
        }

        public KeyPrefixStringRedisSerializer(Charset charset) {
            this.charset = charset;
        }

        @Override
        public String deserialize(byte[] bytes) {
            return (bytes == null ? null : new String(bytes, charset).substring(appConfig.getRedis().getPrefix().length()));
        }

        @Override
        public byte[] serialize(String string) {
            return (string == null ? null : (appConfig.getRedis().getPrefix() + ":" + string).getBytes(charset));
        }
    }
}
