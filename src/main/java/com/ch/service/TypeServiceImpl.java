package com.ch.service;

import com.ch.NotFoundException;
import com.ch.dao.TypeRepository;
import com.ch.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service//service层注解，表明该类是一个service层
public class TypeServiceImpl implements TypeService {

    @Autowired//从dao层注入type数据库对象
    private TypeRepository typeRepository;
    private PageRequest pageRequest;

//    增加一个新类型
    @Transactional//数据库事务注解
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }
//     根据id查询一个类型
//    此处存在一个疑问 教程中使用findOne（id）方法获得type类型对象，但实际会出现报错，所以和依据name获得对象的方法一样在dao层定义了专门的id和name方法
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findByid(id);
    }
//  查询分页列表
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
//        Sort sort = new Sort(Sort.DEFAULT_DIRECTION,"blog.size");
//注意因为对于Sort使用出现bug，所以在controller中并没有使用这个方法去获得数据库的type排列顺序，而是使用了上方的无参数方法将所有的type都列举出来
//        Pageable pageable=new PageRequst(0,size);
        Pageable pageable = pageRequest.previousOrFirst();
        return typeRepository.findTop(pageable);
    }

    //改；更新分类
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t=typeRepository.findByid(id);
        if(t==null){
            throw new NotFoundException("不存在这个类型");
        }
//        借助spring中的工具将type中的类型内容复制到t中，最后保存t，完成类型的更新
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }
//通过id将类型删掉
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
