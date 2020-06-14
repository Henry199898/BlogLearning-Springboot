package com.ch.dao;

import com.ch.po.Tag;

import com.ch.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {
    //    自定义一个通过name和id得到一个tag标签对象的方法
    Tag findByName(String name);
    Tag findByid(Long id);
    //    定义一个根据数据库中tag多少，将tag排顺序，由多到少展示在前台页面(实际情况中Sort方法并没有使用)
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
