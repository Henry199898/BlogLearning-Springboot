package com.ch.web;


import com.ch.po.Comment;
import com.ch.service.BlogService;
import com.ch.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
//    设置一个常量图片作为游客的回复评论头像
    @Value("@{comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogid, Model model){

        model.addAttribute("comment",commentService.listCommentByBlogId(blogid));
        return "blog :: commentList";

    }

//   点击发布评论的相关响应
    @PostMapping("/comments")
    public String post(Comment comment){
//        先建立博客与评论之间的关系
        Long blogId=comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        comment.setAvatar(avatar);
        commentService.saveComment(comment);
        return "redirect:/comments"+comment.getBlog().getId();
    }

}
