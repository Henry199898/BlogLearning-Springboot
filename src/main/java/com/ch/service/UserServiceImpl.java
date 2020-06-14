package com.ch.service;


import com.ch.dao.UserRepository;
import com.ch.po.User;
import com.ch.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service//服务层，业务逻辑层
public class UserServiceImpl implements UserService {

//获得数据层的数据
    @Autowired
    private UserRepository userRepository;

    @Override
//    得去一次数据层验证输入的用户名与密码的正确性
//    此处的password是从前端获得，在与dao层的验证中，使用MD5加密方式转换为MD码与数据库中的MD5比较，以上即为密码验证的过程！！
    public User checkUser(String username, String passworld) {
        User user=userRepository.findByUsernameAndPassword(username, MD5Util.code(passworld));

        return user;
    }
}
