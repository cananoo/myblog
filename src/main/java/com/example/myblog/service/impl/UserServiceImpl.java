package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.pojo.User;
import com.example.myblog.service.UserService;
import com.example.myblog.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2023-08-22 20:00:32
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




