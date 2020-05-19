package com.virgo.gateway.core.filter;

import com.virgo.common.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Order
public class TraceGlobalFilter implements GlobalFilter, Ordered {
    private static final String TRACE_ID = "TRACE_ID";
    private static final SnowflakeIdWorker SNOWFLAKE_ID_WORKER = new SnowflakeIdWorker(1);
    private static final int order = Integer.MIN_VALUE + 1000;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final String traceId = Long.toUnsignedString(SNOWFLAKE_ID_WORKER.nextId(), 36);
        ServerHttpRequest request = exchange.getRequest().mutate().header(TRACE_ID, traceId).build();
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return order;
    }
}
