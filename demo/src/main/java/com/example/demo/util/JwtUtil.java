package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "19970421"; // 비밀 키

    @Autowired
    private UserService userService;
    
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10시간 후 만료
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
    	
        final String extractedEmail = extractUsername(token); // JWT에서 이메일 추출
        
        // 이메일이 데이터베이스에 존재하고, 토큰이 만료되지 않았는지 확인
        return (userService.existsByEmail(extractedEmail) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
    
    public Long getUserIdFromRequest(HttpServletRequest request) {
    	// 쿠키 배열에서 이메일 쿠키 값 가져오기
        Cookie[] cookies = request.getCookies();
        String jwt = "";
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equalsIgnoreCase(cookie.getName())) { // 대소문자 구분 없이 쿠키 이름 확인
                    jwt = cookie.getValue(); // JWT 반환
                }
            }
        }

        // 토큰에서 이메일 추출
        String email = extractUsername(jwt);
        
        User user = null;
        
        if (email != null) {
            user = userService.findByEmail(email); // 댓글등록 유저 아이디는 토큰에서 추출한 이메일 기반으로 설정
        } else {
            return null;
        }
        
        return user.getUserId();
    }
}
