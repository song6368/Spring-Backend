package com.example.demo.service;

import com.example.demo.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);
    List<Comment> getCommentsByPostId(Long postId);
    Comment getCommentById(Long commentId);
    Comment updateComment(Long commentId, Comment comment);
    boolean deleteComment(Long commentId);
}