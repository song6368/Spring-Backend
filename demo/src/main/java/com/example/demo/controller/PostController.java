package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Post;
import com.example.demo.service.PostService;
import com.example.demo.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
    private final PostService postService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시물 목록 조회 (GET /api/posts)
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 게시물 생성 (POST /api/posts)
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, HttpServletRequest request) {
        if (post == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Long userId = jwtUtil.getUserIdFromRequest(request);
        
        post.setUserId(userId);
        
        Post createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    // 특정 게시물 조회 (GET /api/posts/{postId})
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable("postId") Long postId) {
        Optional<Post> post = postService.getPostById(postId);
        return post.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 게시물 업데이트 (PUT /api/posts/{postId})
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable("postId") Long postId, @RequestBody Post post, HttpServletRequest request) {
        if (post == null) {
            return ResponseEntity.badRequest().build();
        }
        
        Long userId = jwtUtil.getUserIdFromRequest(request);
        
        Post updatedPost = postService.updatePost(postId, post, userId);
        return ResponseEntity.ok(updatedPost);
    }

    // 게시물 삭제 (DELETE /api/posts/{postId})
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId, HttpServletRequest request) {
    	Long userId = jwtUtil.getUserIdFromRequest(request);
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }
}