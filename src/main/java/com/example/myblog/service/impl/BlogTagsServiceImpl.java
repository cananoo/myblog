package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.pojo.Blog;
import com.example.myblog.pojo.BlogTags;
import com.example.myblog.service.BlogTagsService;
import com.example.myblog.mapper.BlogTagsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //查询博客数最多的n个标签的id
    public List<Long> findTopTags(int n) {
        List<BlogTags> topTags = blogTagsMapper.selectList(null);
        Map<Long, Integer> map = new HashMap<>();
        for (BlogTags topTag : topTags) {
            if (map.containsKey(topTag.getTagsId())) {
                map.put(topTag.getTagsId(), map.get(topTag.getTagsId()) + 1);
            } else {
                map.put(topTag.getTagsId(), 1);
            }
        }
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int max = 0;
            Long maxId = 0L;
            for (Long id : map.keySet()) {
                if (map.get(id) > max) {
                    max = map.get(id);
                    maxId = id;
                }
            }
            list.add(maxId);
            map.remove(maxId);
        }
        return list;
    }

}




