package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.pojo.Blog;
import com.example.myblog.service.BlogService;
import com.example.myblog.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
* @author ASUS
* @description 针对表【t_blog】的数据库操作Service实现
* @createDate 2023-08-22 20:04:17
*/
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

    @Autowired
    private BlogMapper blogMapper;

    //新增博客
    public int saveBlog(Blog blog) {
        //如果blog的四个属性不为true则为false,防止为null
        if (blog.getRecommend() == null){
            blog.setRecommend(false);
        }
        if (blog.getCommentabled() == null){
            blog.setCommentabled(false);
        }
        if (blog.getShareStatement() == null){
            blog.setShareStatement(false);
        }
        if (blog.getAppreciation() == null){
            blog.setAppreciation(false);
        }
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        int insert = blogMapper.insert(blog);
        return insert;
    }

    //根据id查询博客
    public Blog getBlogById(long id) {
        Blog blog = blogMapper.selectById(id);
        return blog;
    }

    //根据id修改博客
    public int updateBlogById(long id, Blog blog) {
        //如果blog的四个属性不为true则为false,防止为null
        if (blog.getRecommend() == null){
            blog.setRecommend(false);
        }
        if (blog.getCommentabled() == null){
            blog.setCommentabled(false);
        }
        if (blog.getShareStatement() == null){
            blog.setShareStatement(false);
        }
        if (blog.getAppreciation() == null){
            blog.setAppreciation(false);
        }
        blog.setUpdateTime(new Date());
        QueryWrapper<Blog> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("id",id);
        int update = blogMapper.update(blog, queryWrapper);
        return update;
    }

    //根据id删除博客
    public int deleteBlogById(long id) {
        int i = blogMapper.deleteById(id);
        return i;
    }

    //根据博客内容返回固定页面的数据
    public List<Blog> findBlogPage(Page<Blog> page, Blog blog) {
        if (blog == null){
            QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("update_time");
            return  blogMapper.selectPage(page, queryWrapper).getRecords();
        }
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("update_time")
                .like(blog.getTitle()!=null&& !"".equals(blog.getTitle()),"title",blog.getTitle())
                .eq(blog.getTypeId()!=null,"type_id",blog.getTypeId())
                .eq(blog.getRecommend()!=null,"recommend",blog.getRecommend());
        Page<Blog> blogPage = blogMapper.selectPage(page, queryWrapper);
        List<Blog> records = blogPage.getRecords();
        return records;
    }

    //根据name返回博客
    public Blog findBlogByName(String name) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        Blog blog = blogMapper.selectOne(queryWrapper);
        return blog;
    }
}




