package com.example.myblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.pojo.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myblog.pojo.Blog;
import com.example.myblog.pojo.Type;

import java.util.List;

/**
* @author ASUS
* @description 针对表【t_blog】的数据库操作Service
* @createDate 2023-08-22 20:04:17
*/
public interface BlogService extends IService<Blog> {


    //新增博客
    int saveBlog(Blog Blog);

    //根据id查询博客
    Blog getBlogById(long id);

    //根据id修改博客
    int updateBlogById(long id, Blog blog);

    //根据id删除博客
    int deleteBlogById(long id);

    //根据指定内容返回固定页面的数据
    List<Blog> findBlogPage(Page<Blog> page,Blog blog);

    //根据name返回博客
    Blog findBlogByName(String name);

    //返回博客数最多的n个type的id
    List<Long> findTopType(int n);
  //获取所有博客的数目
    long getBlogNum();

    //获取更新时间最近的n个推荐的博客
    List<Blog> findTopRecommend(int n);

    //获取最新的n个博客
    List<Blog> findTopNew(int n);
}
