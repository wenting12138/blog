package com.wenting.blog.service.impl;

import com.wenting.blog.bean.Blog;
import com.wenting.blog.bean.Tag;
import com.wenting.blog.bean.Type;
import com.wenting.blog.dao.BlogDao;
import com.wenting.blog.exception.BlogException;
import com.wenting.blog.service.BlogService;
import com.wenting.blog.utils.MarkdownUtil;
import com.wenting.blog.utils.MyBeanUtil;
import com.wenting.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;


    @Transactional
    public Blog getAndConvert(Long id) {
        Blog blog = blogDao.findOne(id);
        if (blog == null){
            throw new BlogException("博客不存在...");
        }
        Blog result = new Blog();
        BeanUtils.copyProperties(blog, result);
        String content = blog.getContent();
        if (content == null) {
            throw new BlogException("博客内容不存在...");
        }
        String s = MarkdownUtil.markdownToHtmlExtensions(content);
        result.setContent(s);
        blogDao.updateViews(id);
        return result;
    }

    @Override
    public Blog getBlog(Long id) {
        return blogDao.findOne(id);
    }


    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (!StringUtils.isEmpty(blog.getTitle()) && blog.getTitle() != null) {
                    predicateList.add(criteriaBuilder.like(root.<String>get("title"),"%" +blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicateList.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        }, pageable);
    }

    @Transactional
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            blog.setUpdateTime(new Date());
        }
        return blogDao.save(blog);
    }

    @Transactional
    public Blog updateBlog(Long id, Blog blog) {

        Blog one = blogDao.findOne(id);
        if (one == null) {
            throw new BlogException("查询的博客不存在");
        }
        BeanUtils.copyProperties(blog, one, MyBeanUtil.getNullPropertiesNames(blog));
        one.setUpdateTime(new Date());
        return blogDao.save(one);
    }

    @Transactional
    public void deleteBlog(Long id) {
        blogDao.delete(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogDao.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, String query) {
        return blogDao.findByQuery(query, pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(0, size ,sort);
        return blogDao.findTop(pageable);

    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tagList");
                return criteriaBuilder.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogDao.findGroupYears();
        Map<String, List<Blog>> blogMap = new HashMap<>();
        for (String year : years) {
            blogMap.put(year,blogDao.findByYear(year));
        }
        return blogMap;
    }

    @Override
    public Long countBlog() {
        return blogDao.count();
    }
}
