package com.example.demo.service.impl;

import com.example.demo.entity.Comment;
import com.example.demo.repository.CommentRepository;
import com.example.demo.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Comment createComment(Comment comment) {
		comment.setCreatedAt(LocalDateTime.now());
		comment.setUpdatedAt(LocalDateTime.now());
		return commentRepository.save(comment);
	}

	@Override
	public List<Comment> getCommentsByPostId(Long postId) {
		return commentRepository.findByPostId(postId);
	}

	@Override
	public Comment getCommentById(Long commentId) {
		return commentRepository.findById(commentId).orElse(null);
	}

	@Override
	public Comment updateComment(Long commentId, Comment comment, Long userId) {
		Comment existingComment = getCommentById(commentId);

		if (existingComment.getUserId().equals(userId)) {
			existingComment.setContent(comment.getContent());
			existingComment.setUpdatedAt(LocalDateTime.now());
			return commentRepository.save(existingComment);
		}

		return null; // 댓글이 존재하지 않을 경우 null 반환
	}

	@Override
	public boolean deleteComment(Long commentId, Long userId) {

		Comment existingComment = getCommentById(commentId);
		
		if (commentRepository.existsById(commentId)) {
			if (existingComment.getUserId().equals(userId)) {
				commentRepository.deleteById(commentId);
				return true; // 삭제 성공
			}
		}
		
		return false; // 삭제할 댓글이 존재하지 않을 경우 false 반환
	}
}
