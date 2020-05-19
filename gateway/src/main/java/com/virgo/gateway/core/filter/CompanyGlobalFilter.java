package com.virgo.gateway.core.filter;

import com.virgo.common.JsonUtils;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 根据request 请求host name查询相对应company code 并放入header
 * 如  480cbfeb.nat123.fun转换成 1000001放入request header
 */
@Slf4j
@Component
public class CompanyGlobalFilter implements GlobalFilter, Ordered {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    private static final int order = Integer.MIN_VALUE + 800;
    @Resource
    private JdbcTemplate jdbcTemplate;
    private static final String FORBIDDEN_FLAG = "-1";
    private static final String REDIS_PREFIX = "host:";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String host = exchange.getRequest().getURI().getHost();
        String companyCode = redisTemplate.opsForValue().get(REDIS_PREFIX + host);//redis查询

        if (companyCode == null) {
            //redis 查询为空，从数据库中查询
            List<String> data = jdbcTemplate.queryForList("SELECT code FROM company WHERE host = '" + host + "'", String.class);
            if (CollectionUtils.isEmpty(data)) {
                log.error("未找到域名相对应的公司 {}", host);
                redisTemplate.opsForValue().set(REDIS_PREFIX + host, FORBIDDEN_FLAG, 20, TimeUnit.SECONDS);//未找到域名相对应的公司 设置查询flag 防止重复刷库 20s
                return forbiddenHost(exchange);
            }
            companyCode = data.get(0);
            redisTemplate.opsForValue().set(REDIS_PREFIX + host, companyCode, 10, TimeUnit.DAYS);
        }

        if (Objects.equals(companyCode, FORBIDDEN_FLAG)) {
            return forbiddenHost(exchange);

        }
        ServerHttpRequest request = exchange.getRequest().mutate().header("COMPANY_CODE", companyCode).build();
        return chain.filter(exchange.mutate().request(request).build());
    }

    private Mono<Void> forbiddenHost(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.OK);//使用子状态码判断
        return exchange.getResponse()
                .writeAndFlushWith(
                        Flux.just(ByteBufFlux.just(
                                exchange.getResponse().bufferFactory().wrap(JsonUtils.kvpToJson("status", 40003, "message", "no company code found by host").getBytes()))));
    }

    @Override
    public int getOrder() {
        return order;
    }
}
