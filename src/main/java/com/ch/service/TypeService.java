package com.ch.service;


import com.ch.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

//分类service层接口；与dao层查询数据相结合工作
public interface TypeService {
//    增
    Type saveType(Type type);
//    根据id查
    Type getType(Long id);
//    分页查询
    Page<Type> listType(Pageable pageable);
//    通过名称查询一个类型
    Type getTypeByName(String name);
//    返回所有的类型，在blogs页面中的表单组件中展示
    List<Type> listType();
//    前台展示页面用于确定展示几个类型
    List<Type> listTypeTop(Integer size);
//    改
    Type updateType(Long id,Type type);
//    删除
    void deleteType(Long id);


}
