package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.pojo.BlogTags;
import com.example.myblog.service.BlogTagsService;
import com.example.myblog.mapper.BlogTagsMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【t_blog_tags】的数据库操作Service实现
* @createDate 2023-09-03 16:40:56
*/
@Service
public class BlogTagsServiceImpl extends ServiceImpl<BlogTagsMapper, BlogTags>
    implements BlogTagsService{

//新增博客标签映射
public int saveBlogTags(BlogTags blogTags) {
    int insert = baseMapper.insert(blogTags);
    return insert;
}

}




