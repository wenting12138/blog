package com.wenting.blog.controller;


import com.wenting.blog.bean.Comment;
import com.wenting.blog.bean.User;
import com.wenting.blog.service.BlogService;
import com.wenting.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog_detail :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAdmin(true);
            comment.setAvatar(user.getAvatar());
//            comment.setNickName(user.getNickName());
        }else {
            comment.setAvatar(avatar);
            comment.setAdmin(false);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

}
