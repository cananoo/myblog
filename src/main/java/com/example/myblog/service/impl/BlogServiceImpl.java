package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.pojo.Blog;
import com.example.myblog.service.BlogService;
import com.example.myblog.mapper.BlogMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【t_blog】的数据库操作Service实现
* @createDate 2023-08-22 20:04:17
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

}




