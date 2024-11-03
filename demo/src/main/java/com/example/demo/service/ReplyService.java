package com.example.demo.service;

import com.example.demo.entity.Reply;
import java.util.List;

public interface ReplyService {
    Reply saveReply(Reply reply);
    List<Reply> getRepliesByCommentId(Long commentId);
    Reply updateReply(Long replyId, String content, Long userId);
    void deleteReply(Long replyId, Long userId);
}