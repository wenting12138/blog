package com.wenting.blog.service.impl;

import com.wenting.blog.bean.User;
import com.wenting.blog.dao.UserDao;
import com.wenting.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, password);
        return user;
    }
}
