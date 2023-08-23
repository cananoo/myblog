package com.example.myblog.mapper;

import com.example.myblog.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author ASUS
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2023-08-22 20:00:32
* @Entity com.example.myblog.pojo.User
*/
@Repository
public interface UserMapper extends BaseMapper<User> {

}




