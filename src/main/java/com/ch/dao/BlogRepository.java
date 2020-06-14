package com.ch.dao;

import com.ch.po.Blog;
import com.ch.po.Tag;
import org.jboss.logging.BasicLogger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository  extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {


    Blog findByid(Long id);

    //    定义一个根据数据库中tag多少，将tag排顺序，由多到少展示在前台页面(实际情况中Sort方法并没有使用)
    @Query("select t from Tag t")
    List<Blog> findTop(Pageable pageable);

//    自定义一个全局搜索的方法
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query,Pageable pageable);
}
