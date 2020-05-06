package com.virgo.common.web;

import com.virgo.common.JsonUtils;
import com.virgo.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Aspect
@Slf4j
public class ControllerAOP {

    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress net;
                try {
                    net = InetAddress.getLocalHost();
                    ip = net.getHostAddress();
                } catch (UnknownHostException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    @Around("(execution( * com..controller.*Controller.*(..)))))")
    public Object aop(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Optional<HttpServletRequest> request = getRequest();
        Method currentMethod = getMethod(joinPoint);
        request.ifPresent(httpServletRequest -> log.info("拦截方法:{}.{} 调用方ip: {}",
                currentMethod.getDeclaringClass().getSimpleName(), currentMethod.getName(), getIpAddress(httpServletRequest)));
        Object[] args = joinPoint.getArgs();
        try {
            List params = Arrays.stream(args).filter(arg -> !(arg instanceof ServletRequest)).collect(Collectors.toList());
            log.info("请求参数为：{}", JsonUtils.toJson(params));
        } catch (Exception e) {
            log.error("解析参数异常", e);
        }
        Object result;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
            if (log.isDebugEnabled()) {
                log.debug("返回结果为：{}", JsonUtils.toJson(result));
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error("Controller执行异常！");
            throw new SystemException(e);
        } finally {
            long executeTime = (System.currentTimeMillis() - startTime);
            if (executeTime > 500)
                log.warn("方法{}.{} 执行时间 {}ms", currentMethod.getDeclaringClass().getSimpleName(), currentMethod.getName(), executeTime);
            else
                log.info("方法{}.{} 执行时间 {}ms", currentMethod.getDeclaringClass().getSimpleName(), currentMethod.getName(), executeTime);
        }
        return result;
    }

    private Optional<HttpServletRequest> getRequest() {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) return Optional.empty();
            return Optional.of(requestAttributes.getRequest());
        } catch (Exception e) {
            log.error("request 未获取到");
            return Optional.empty();
        }
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Signature sig = joinPoint.getSignature();
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        MethodSignature mSig = (MethodSignature) sig;
        Object target = joinPoint.getTarget();
        return target.getClass().getMethod(mSig.getName(), mSig.getParameterTypes());
    }
}
