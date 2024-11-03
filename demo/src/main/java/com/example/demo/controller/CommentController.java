package com.example.demo.controller;

import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.service.CommentService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    // 댓글 생성
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromRequest(request);

        // 쿠키의 이메일 정보를 통해 알아낸 userId값 사용
        comment.setUserId(userId);

        // 댓글 생성
        Comment createdComment = commentService.createComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    // 특정 게시물의 댓글 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable("postId") Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    // 특정 댓글 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable("commentId") Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return comment != null ? ResponseEntity.ok(comment) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable("commentId") Long commentId, @RequestBody Comment comment, HttpServletRequest request) {
    	Long userId = jwtUtil.getUserIdFromRequest(request);
        Comment updatedComment = commentService.updateComment(commentId, comment, userId);
        return updatedComment != null ? ResponseEntity.ok(updatedComment) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId, HttpServletRequest request) {
    	
    	Long tokenUserId = jwtUtil.getUserIdFromRequest(request);
    	
        if (commentService.deleteComment(commentId, tokenUserId)) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
