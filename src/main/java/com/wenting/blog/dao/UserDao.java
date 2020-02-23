package com.wenting.blog.dao;


import com.wenting.blog.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {

    /**
     *  登录
     */
    User findByUsernameAndPassword(String username, String password);

}
