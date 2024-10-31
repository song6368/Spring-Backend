// UserRepository.java
package com.example.demo.repository;

import com.example.demo.entity.User; // User 엔티티 클래스 가져오기
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email); // 이메일 존재 여부 확인 메서드

	User findByEmail(String email); // 이메일로 사용자 찾기
}