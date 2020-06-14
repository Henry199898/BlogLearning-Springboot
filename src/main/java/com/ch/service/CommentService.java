package com.ch.service;


import com.ch.po.Comment;
import java.util.List;


public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);
    Comment saveComment(Comment comment);
}
