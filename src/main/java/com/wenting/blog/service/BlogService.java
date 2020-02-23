package com.wenting.blog.service;

import com.wenting.blog.bean.Blog;
import com.wenting.blog.bean.Tag;
import com.wenting.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getAndConvert(Long id);

    Blog getBlog(Long id);

    // 分类多条件查询
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Pageable pageable, String query);

    List<Blog> listRecommendBlogTop(Integer size);

    Page<Blog> listBlog(Long tagId, Pageable pageable);


    Map<String, List<Blog>> archiveBlog();

    Long countBlog();

}
