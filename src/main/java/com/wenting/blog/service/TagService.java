package com.wenting.blog.service;

import com.wenting.blog.bean.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    // 分页
    Page<Tag> getTagAll(Pageable pageable);

    // 新增
    Tag saveTag(Tag tag);

    // 修改
    Tag update(Long id, Tag tag);

    // 查询
    Tag findTagById(Long id);

    // 删除
    void deleteTagById(Long id);

    // 按照名字查
    Tag findByName(String name);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);


}
