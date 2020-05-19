package com.virgo.gateway.core.filter;

import com.virgo.common.JsonUtils;
import com.virgo.gateway.dto.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class AuthParseGlobalFilter implements GatewayFilter, Ordered {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    private static final String prefix = "user:token:";
    private static final String MEMBER_ID = "MEMBER_ID";
    private static final String USER_ID = "USER_ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> authorizations = exchange.getRequest().getHeaders().get("Authorization"); //Bearer {{token}}
        if (CollectionUtils.isEmpty(authorizations)) {
            //todo no auth
        } else {
            String token = authorizations.get(0).substring(7);
            String userString = redisTemplate.opsForValue().get(prefix + token);
            Member member = JsonUtils.parse(userString, Member.class);

            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header(MEMBER_ID, member.getMemberId())
                    .header(USER_ID, member.getId() + "").build();
            return chain.filter(exchange.mutate().request(request).build());
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}