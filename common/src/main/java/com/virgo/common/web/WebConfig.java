package com.virgo.common.web;

import com.virgo.common.RequestHolder;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String MEMBER_ID = "MEMBER_ID";
    private static final String COMPANY_CODE = "COMPANY_CODE";
    private static final String TRACE_ID = "TRACE_ID";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                try {
                    String memberId = request.getHeader(MEMBER_ID);
                    String companyCode = request.getHeader(COMPANY_CODE);
                    RequestHolder.setMemberId(memberId);
                    RequestHolder.setCompanyCode(companyCode);
                    String traceId = request.getHeader(TRACE_ID);
                    MDC.put(TRACE_ID, StringUtils.isEmpty(traceId) ? "" : "[" + traceId + "]");
                } catch (Exception e) {
                    MDC.remove(TRACE_ID);
                    RequestHolder.clearAll();
                    throw e;
                }
                return true;
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                MDC.remove(TRACE_ID);
                RequestHolder.clearAll();
            }
        }).addPathPatterns("/**").order(0);

    }
}
