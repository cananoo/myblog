package com.example.myblog.service;

import com.example.myblog.pojo.BlogTags;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ASUS
* @description 针对表【t_blog_tags】的数据库操作Service
* @createDate 2023-09-03 16:40:56
*/
public interface BlogTagsService extends IService<BlogTags> {

    //新增博客标签映射
    int saveBlogTags(BlogTags blogTags);



}
