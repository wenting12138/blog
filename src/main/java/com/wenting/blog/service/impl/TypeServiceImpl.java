package com.wenting.blog.service.impl;

import com.wenting.blog.bean.Type;
import com.wenting.blog.dao.TypeDao;
import com.wenting.blog.exception.BlogException;
import com.wenting.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Transactional
    public Type saveType(Type type) {
        return typeDao.save(type);
    }

    @Override
    public Type getType(Long type) {
        return typeDao.findOne(type);
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeDao.findAll(pageable);
    }

    @Transactional
    public Type updateType(Long id, Type type) {
        Type one = typeDao.findOne(id);
        if (one == null){
            throw new BlogException("类型不存在");
        }
        BeanUtils.copyProperties(type, one);
        return typeDao.save(one);
    }

    @Transactional
    public void deleteType(Long id) {
        typeDao.delete(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.findByName(name);
    }

    @Override
    public List<Type> listType() {
        return typeDao.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "blogList.size");
        Pageable pageable = new PageRequest(0, size, sort);
        return typeDao.findTop(pageable);
    }
}
