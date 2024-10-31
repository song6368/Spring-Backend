package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // PasswordEncoder 주입
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // createdAt과 updatedAt이 null인 경우 현재 시간으로 설정
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }
        if (user.getUpdatedAt() == null) {
            user.setUpdatedAt(LocalDateTime.now());
        }
        
        // 비밀번호 암호화
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return userRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // 로그인 성공 시 JWT 생성
            String token = jwtUtil.generateToken(user.getEmail());

            // JWT를 쿠키로 설정
            Cookie cookie = new Cookie("JWT", token);
            cookie.setHttpOnly(true); // JavaScript에서 접근할 수 없도록 설정
            cookie.setSecure(true); // HTTPS를 사용할 때만 쿠키 전송
            cookie.setPath("/"); // 쿠키의 유효 경로 설정
            cookie.setMaxAge(60 * 60 * 10); // 쿠키 만료 시간 설정 (10시간)

            response.addCookie(cookie); // 응답에 쿠키 추가
            return ResponseEntity.ok("Login successful"); // 로그인 성공 응답
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}