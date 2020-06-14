package com.ch.service;


import com.ch.po.Blog;
import com.ch.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//后台博客的增删改查进行service层的定义
public interface BlogService {

//    根据id查询一个博客
    Blog getBlog(Long id);
//   博客详情页面获取一个markdown格式的文本展示
    Blog getAndConvert(Long id);

//    根据分类条件分页查询博客，并将查询的条件封装为一个BLog对象传递进该方法
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
//  仅展示blog在前端中的接口(前台展示中使用index)
    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> listBlogTop(Integer size);

//    新增一篇博客
    Blog saveBlog(Blog blog);

//    全局查询博客返回一个pageable页面
    Page<Blog> listBlog(String query,Pageable pageable);

//   更新修改一篇博客
    Blog updateBlog(Long id,Blog blog);

//    删除一篇博客
    void deleteBlog(Long id);


}
