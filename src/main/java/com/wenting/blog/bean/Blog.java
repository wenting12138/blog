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
@Table(name = "t_blog")
public class Blog {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Lob // 大字段类型
    @Basic(fetch = FetchType.LAZY)
    private String content;
    private String firstPicture;

    @Transient
    private String tagIds;
    // 标识
    private String flag;
    // 浏览次数
    private Integer views;

    private String description;

    // 赞赏是否开启
    private boolean appreciation;
    // 转载声名
    private boolean shareStatement;
    // 评论是否开启
    private boolean commentabled;

    // 发布
    private boolean published;
    // 是否推荐
    private boolean recommend;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Type type;

    @ManyToMany(cascade = {CascadeType.PERSIST}) // 级联新增
    private List<Tag> tagList = new ArrayList<>();

    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "blog")
    private List<Comment> commentList = new ArrayList<>();

    public void init(){
        this.tagIds = tagsToIds(this.tagList);
    }

    private String tagsToIds(List<Tag> tagList) {
        StringBuffer ids = null;
        if (!tagList.isEmpty()) {
            ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tagList) {
                if (flag){
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else {
            return tagIds;
        }
    }


}
