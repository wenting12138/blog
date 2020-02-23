package com.wenting.blog.service;

import com.wenting.blog.bean.Comment;

import java.util.List;

public interface CommentService {


    List<Comment> listCommentByBlogId(Long BlogId);

    Comment saveComment(Comment comment);

}
