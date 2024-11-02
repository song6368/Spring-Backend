package com.example.demo.repository;

import com.example.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시물에 대한 댓글을 조회하는 메서드
    List<Comment> findByPostId(Long postId);

    // 특정 사용자의 댓글을 조회하는 메서드 (선택 사항)
    List<Comment> findByUserId(Long userId);
}