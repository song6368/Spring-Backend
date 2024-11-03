package com.example.demo.repository;

import com.example.demo.entity.Reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

	List<Reply> findByCommentId(Long commentId);
	
}
