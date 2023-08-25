package com.example.myblog.mapper;

import com.example.myblog.pojo.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
* @author ASUS
* @description 针对表【t_blog】的数据库操作Mapper
* @createDate 2023-08-22 20:04:17
* @Entity com.example.myblog.pojo.Blog
*/
@Component
public interface BlogMapper extends BaseMapper<Blog> {

}




