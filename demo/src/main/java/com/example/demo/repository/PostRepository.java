package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
    // 제목으로 게시글 검색
    List<Post> findByTitleContaining(String keyword);

    // 특정 사용자의 모든 게시글 조회
    List<Post> findByUserId(Long userId);

    // 최신 게시글 상위 5개 조회
    List<Post> findTop5ByOrderByCreatedAtDesc();

    // 사용자 ID와 게시글 제목으로 게시글 검색 (JPQL 사용)
    @Query("SELECT p FROM Post p WHERE p.userId = :userId AND p.title LIKE %:keyword%")
    List<Post> searchByUserIdAndTitle(Long userId, String keyword);
}