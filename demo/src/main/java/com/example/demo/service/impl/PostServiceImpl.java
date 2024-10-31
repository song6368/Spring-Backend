package com.example.demo.service.impl;

import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 모든 게시물 조회
    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // 게시물 생성
    @Override
    public Post createPost(Post post) {
    	
    	if(post.getCreatedAt() == null) {
    		post.setCreatedAt(LocalDateTime.now());
    	}
    	
    	if(post.getUpdatedAt() == null) {
    		post.setUpdatedAt(LocalDateTime.now());
    	}
    	
        return postRepository.save(post);
    }

    // 특정 게시물 조회
    @Override
    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    // 게시물 업데이트
    @Override
    public Post updatePost(Long postId, Post post) {
        Post existingPost = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setUpdatedAt(post.getUpdatedAt());
        
        return postRepository.save(existingPost);
    }

    // 게시물 삭제
    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    // 제목으로 게시물 검색
    @Override
    public List<Post> findByTitleContaining(String keyword) {
        return postRepository.findByTitleContaining(keyword);
    }

    // 특정 사용자의 모든 게시물 조회
    @Override
    public List<Post> findByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    // 최신 게시물 상위 5개 조회
    @Override
    public List<Post> findTop5ByOrderByCreatedAtDesc() {
        return postRepository.findTop5ByOrderByCreatedAtDesc();
    }

    // 사용자 ID와 제목으로 게시물 검색
    @Override
    public List<Post> searchByUserIdAndTitle(Long userId, String keyword) {
        return postRepository.searchByUserIdAndTitle(userId, keyword);
    }
}