package com.wenting.blog.service.impl;

import com.wenting.blog.bean.Tag;
import com.wenting.blog.dao.TagDao;
import com.wenting.blog.exception.BlogException;
import com.wenting.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public Page getTagAll(Pageable pageable) {
        return tagDao.findAll(pageable);
    }

    @Transactional
    public Tag saveTag(Tag tag) {
        return tagDao.save(tag);
    }

    @Transactional
    public Tag update(Long id, Tag tag) {
        Tag one = tagDao.findOne(id);
        if (one == null) {
            throw new BlogException("无此标签");
        }
        BeanUtils.copyProperties(one, tag);

        return tagDao.save(one);
    }

    @Override
    public Tag findTagById(Long id) {
        return tagDao.findOne(id);
    }

    @Transactional
    public void deleteTagById(Long id) {
        tagDao.delete(id);
    }

    @Override
    public Tag findByName(String name) {
        return tagDao.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        return tagDao.findAll(convertToList(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "blogList.size");
        Pageable pageable = new PageRequest(0, size, sort);
        return tagDao.findTop(pageable);
    }



    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!StringUtils.isEmpty(ids) && ids != null){
            String[] strings = ids.split(",");
            for (int i = 0; i < strings.length; i++) {
                list.add(Long.valueOf(strings[i]));
            }
        }
        return list;
    }
}
