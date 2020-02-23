package com.wenting.blog.bean;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "t_comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    private String nickName;

    private String email;
    private String content;
    // 图片链接地址
    private String avatar;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private boolean admin;

    @ManyToOne()
    private Blog blog;
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyCommentList = new ArrayList<>();
    @ManyToOne
    private Comment parentComment;

}
