package com.example.demo.service;

import com.example.demo.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    
    // 모든 게시물 조회
    List<Post> getAllPosts();

    // 게시물 생성
    Post createPost(Post post);

    // 특정 게시물 조회
    Optional<Post> getPostById(Long postId);

    // 게시물 업데이트
    Post updatePost(Long postId, Post post, Long userId);

    // 게시물 삭제
    void deletePost(Long postId, Long userId);

    // 제목으로 게시물 검색
    List<Post> findByTitleContaining(String keyword);

    // 특정 사용자의 모든 게시물 조회
    List<Post> findByUserId(Long userId);

    // 최신 게시물 상위 5개 조회
    List<Post> findTop5ByOrderByCreatedAtDesc();

    // 사용자 ID와 제목으로 게시물 검색
    List<Post> searchByUserIdAndTitle(Long userId, String keyword);
}