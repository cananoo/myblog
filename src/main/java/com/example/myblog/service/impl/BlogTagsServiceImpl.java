package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.pojo.BlogTags;
import com.example.myblog.service.BlogTagsService;
import com.example.myblog.mapper.BlogTagsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ASUS
* @description 针对表【t_blog_tags】的数据库操作Service实现
* @createDate 2023-09-03 16:40:56
*/
@Service
public class BlogTagsServiceImpl extends ServiceImpl<BlogTagsMapper, BlogTags>
    implements BlogTagsService{

    @Autowired
    private BlogTagsMapper blogTagsMapper;

//新增博客标签映射
public int saveBlogTags(BlogTags blogTags) {
    int insert = blogTagsMapper.insert(blogTags);
    return insert;
}

//删除博客标签映射
    public  int deleteByBlogId(Long blogId){
        QueryWrapper<BlogTags> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blogs_id",blogId);
        int i = blogTagsMapper.delete(queryWrapper);
        return i;
    }
    //根据博客id得到博客标签映射并以逗号封装成字符串
    public String getBlogTagsByBlogId(Long blogId){
        QueryWrapper<BlogTags> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blogs_id",blogId);
        List<BlogTags> blogTags = blogTagsMapper.selectList(queryWrapper);
        String tags = "";
        for (BlogTags blogTags1 : blogTags) {
            tags += blogTags1.getTagsId() + ",";
        }
        //去除最后一个逗号
        tags = tags.substring(0,tags.length()-1);
        return tags;
    }
}




