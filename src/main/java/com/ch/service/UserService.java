package com.ch.service;


import com.ch.po.User;

//用户登录后端代码
public interface UserService {

    User checkUser(String username, String passworld);
}
