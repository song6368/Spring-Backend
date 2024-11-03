package com.example.demo.controller;

import com.example.demo.entity.Reply;
import com.example.demo.service.ReplyService;
import com.example.demo.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replies")
public class ReplyController {

    @Autowired
    private ReplyService replyService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<Reply> createReply(@RequestBody Reply reply, HttpServletRequest request) {
    	
    	Long userId = jwtUtil.getUserIdFromRequest(request);

        // 쿠키의 이메일 정보를 통해 알아낸 userId값 사용
        reply.setUserId(userId);
    	
        Reply savedReply = replyService.saveReply(reply);
        
        return ResponseEntity.ok(savedReply);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<Reply>> getRepliesByCommentId(@PathVariable("commentId") Long commentId) {
        List<Reply> replies = replyService.getRepliesByCommentId(commentId);
        return ResponseEntity.ok(replies);
    }

    @PutMapping("/{replyId}")
    public ResponseEntity<Reply> updateReply(@PathVariable("replyId") Long replyId, @RequestBody String content, HttpServletRequest request) {
    	Long userId = jwtUtil.getUserIdFromRequest(request);
        Reply updatedReply = replyService.updateReply(replyId, content, userId);
        return ResponseEntity.ok(updatedReply);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable("replyId") Long replyId, HttpServletRequest request) {
    	Long userId = jwtUtil.getUserIdFromRequest(request);
        replyService.deleteReply(replyId, userId);
        return ResponseEntity.noContent().build();
    }
}