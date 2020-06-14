package com.ch.dao;


import com.ch.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

//dao层是与数据库相关的代码层
//利用spring框架中的JPA，但是还不知道作用是啥
public interface UserRepository extends JpaRepository<User,Long> {

//    基本的增删改查
    User findByUsernameAndPassword(String username,String password);


}
