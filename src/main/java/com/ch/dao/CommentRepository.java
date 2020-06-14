package com.ch.dao;

import com.ch.po.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

//    自定义查询方法
    List<Comment> findByBlogId(Long blogId);
    Optional<Comment> findById(Long id);

}

