package com.wenting.blog.service.impl;

import com.wenting.blog.bean.Comment;
import com.wenting.blog.dao.CommentDao;
import com.wenting.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {


    private List<Comment> tempReplys = new ArrayList<>();

    @Autowired
    private CommentDao commentDao;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = new Sort("createTime");
        List<Comment> commentList = commentDao.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(commentList);
    }


    private List<Comment> eachComment(List<Comment> commentList) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : commentList) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }

    private void combineChildren(List<Comment> commentsView) {
        for (Comment comment : commentsView) {
            List<Comment> replyCommentList = comment.getReplyCommentList();
            for (Comment reply : replyCommentList) {
                recursively(reply);
            }
            comment.setReplyCommentList(tempReplys);
            // 清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    // 递归找到所有子元素
    private void recursively(Comment s) {
        tempReplys.add(s);
        if (s.getReplyCommentList().size() > 0){
            List<Comment> replyCommentList = s.getReplyCommentList();
            for (Comment comment : replyCommentList) {
                tempReplys.add(comment);
                if (comment.getReplyCommentList().size()>0){
                    recursively(comment);
                }
            }
        }
    }

    @Transactional
    public Comment saveComment(Comment comment) {
        long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentDao.findOne(parentCommentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentDao.save(comment);
    }
}
