package com.example.demo.aop;

import com.example.demo.dto.LoginRequest;
import com.example.demo.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@ControllerAdvice(annotations = RestController.class)
public class JwtAuthAspect {

    @Autowired
    private JwtUtil jwtUtil;

    @Around("execution(* com.example.demo.controller.PostController.*(..)) || " +
            "execution(* com.example.demo.controller.CommentController.*(..)) || ")
    public Object controllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        checkJwtAuthorization(); // JWT 인증 체크

        // 인증에 성공하면 원래 메서드 실행
        return joinPoint.proceed();
    }

    private void checkJwtAuthorization() {
        // 현재 스레드의 HttpServletRequest 객체를 가져옵니다.
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new JwtAuthenticationException("Request attributes not found");
        }

        HttpServletRequest request = attributes.getRequest();
        String jwt = extractJwtFromCookies(request); // 쿠키에서 JWT 추출

        // JWT가 없으면 예외 발생
        if (jwt == null) {
            throw new JwtAuthenticationException("Missing JWT token in cookies");
        }

        // JWT 유효성 검사
        if (!jwtUtil.validateToken(jwt)) {
            throw new JwtAuthenticationException("Invalid JWT token");
        }
    }

    private String extractJwtFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equalsIgnoreCase(cookie.getName())) { // 대소문자 구분 없이 쿠키 이름 확인
                    return cookie.getValue(); // JWT 반환
                }
            }
        }
        return null; // 쿠키에서 JWT를 찾지 못한 경우 null 반환
    }

    public static class JwtAuthenticationException extends RuntimeException {
        public JwtAuthenticationException(String message) {
            super(message);
        }
    }
}