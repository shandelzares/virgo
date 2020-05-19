package com.virgo.gateway.core.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CompanyFilter implements GlobalFilter, Ordered {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    private static final int order = Integer.MIN_VALUE + 800;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String host = exchange.getRequest().getURI().getHost();
        String companyCode = redisTemplate.opsForValue().get(host);
        if (companyCode == null) {
            List<String> data = jdbcTemplate.queryForList("SELECT code FROM company WHERE host = '" + host + "'", String.class);
            companyCode = data.get(0);
            if (CollectionUtils.isEmpty(data)) {
                log.error("未找到域名相对应的公司 {}", host);
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }
            redisTemplate.opsForValue().set(host, companyCode, 10, TimeUnit.DAYS);
        }
        ServerHttpRequest request = exchange.getRequest().mutate().header("COMPANY_CODE", companyCode).build();
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return order;
    }
}
