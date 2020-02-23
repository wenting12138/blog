package com.wenting.blog.dao;

import com.wenting.blog.bean.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeDao extends JpaRepository<Type, Long> {

    Type findByName(String name);
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);

}
