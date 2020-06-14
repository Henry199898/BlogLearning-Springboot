package com.ch.dao;

import com.ch.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {

//    自定义一个通过name和id得到一个type类型对象的方法
    Type findByName(String name);
    Type findByid(Long id);
//    定义一个根据数据库中type多少，将type排顺序，由多到少展示在前台页面
    @Query("select t from Type t")
    List<Type>  findTop(Pageable pageable);
}
