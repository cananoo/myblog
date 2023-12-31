package com.example.myblog.service;

import com.example.myblog.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ASUS
* @description 针对表【t_user】的数据库操作Service
* @createDate 2023-08-22 20:00:32
*/
public interface UserService extends IService<User> {

 //登录验证
 User checkUser(String username,String password);

 //根据用户名查询用户
    User findUserByName(String username);

}
