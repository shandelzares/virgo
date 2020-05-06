package com.virgo.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Component
public class JsonUtils implements ApplicationContextAware {

    private static ObjectMapper objectMapper;
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        JsonUtils.context = applicationContext;
        objectMapper = context.getBean(ObjectMapper.class);
    }

    public ObjectMapper objectMapper() {
        return objectMapper;
    }

    public static String toJson(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用可变参数动态拼接成map进行转换成json字符串
     */
    public static String kvpToJson(Object... data) {
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < data.length; i += 2) {
            if (i + 1 < data.length)
                map.put(data[i], data[i + 1]);
            else
                map.put(data[i], null);
        }
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    public static String kvpToJsonIgnoreNull(Object... data) {
        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < data.length; i += 2) {
            if (i + 1 < data.length)
                map.put(data[i], data[i + 1]);
        }
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    public static <T> T parse(String data, Class<T> clazz) {
        try {
            return objectMapper.readValue(data, clazz);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static <T> T parse(String data, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(data, valueTypeRef);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
