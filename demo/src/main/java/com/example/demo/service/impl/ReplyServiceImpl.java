package com.example.demo.service.impl;

import com.example.demo.entity.Reply;
import com.example.demo.repository.ReplyRepository;
import com.example.demo.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public Reply saveReply(Reply reply) {
        return replyRepository.save(reply);
    }

    @Override
    public List<Reply> getRepliesByCommentId(Long commentId) {
        return replyRepository.findByCommentId(commentId);
    }

    @Override
    public Reply updateReply(Long replyId, String content, Long userId) {
        Optional<Reply> replyOptional = replyRepository.findById(replyId);
        
        if (replyOptional.isPresent()) {
            Reply reply = replyOptional.get();
            
            // 현재 사용자 ID와 댓글 작성자 ID 비교
            if(reply.getUserId().equals(userId)) {
                reply.setContent(content);
                reply.setUpdatedAt(LocalDateTime.now());
                return replyRepository.save(reply);
            } else {
                throw new RuntimeException("Unauthorized to update this reply.");
            }
             
        } else {
            throw new RuntimeException("Reply not found with id " + replyId);
        }
    }

    @Override
    public void deleteReply(Long replyId, Long userId) {
        Optional<Reply> replyOptional = replyRepository.findById(replyId);
        
        if (replyOptional.isPresent()) {
            Reply reply = replyOptional.get();
            
            // 현재 사용자 ID와 댓글 작성자 ID 비교
            if(reply.getUserId().equals(userId)) {
                replyRepository.deleteById(replyId);
            } else {
                throw new RuntimeException("Unauthorized to delete this reply.");
            }
             
        } else {
            throw new RuntimeException("Reply not found with id " + replyId);
        }
    }
}
