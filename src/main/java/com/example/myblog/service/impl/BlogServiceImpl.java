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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        //截取Content "##### 描述:" 到 "##### 正文:" 之间的内容
        String content = blog.getContent();
        int i = content.indexOf("##### 描述:");
        int i1 = content.indexOf("##### 正文:");

        String substring = content.substring(i + 9, i1);
        //缩短描述长度后面用...代替
        if (substring.length()>100){
            substring = substring.substring(0, 150);
            //后面用...代替
            substring = substring+"...";
        }


        blog.setDescription(substring);
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
        //截取Content "##### 描述:" 到 "##### 正文:" 之间的内容
        String content = blog.getContent();
        int i = content.indexOf("##### 描述:");
        int i1 = content.indexOf("##### 正文:");
        String substring = content.substring(i + 9, i1);
        //缩短描述长度后面用...代替
        if (substring.length()>100){
            substring = substring.substring(0, 150);
            //后面用...代替
            substring = substring+"...";
        }
        blog.setDescription(substring);
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

    //返回博客数最多的n个type的id
    public List<Long> findTopType(int n){
        List<Blog> list = blogMapper.selectList(null);;
        Map<Long, Integer> map = new java.util.HashMap<>();
        for (Blog blog : list) {
            Long typeId = blog.getTypeId();
            if (map.containsKey(typeId)){
                map.put(typeId,map.get(typeId)+1);
            }else {
                map.put(typeId,1);
            }
        }
        List<Long> list1 = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            int max = 0;
            Long maxId = null;
            for (Long aLong : map.keySet()) {
                if (map.get(aLong)>max){
                    max = map.get(aLong);
                    maxId = aLong;
                }
            }
            list1.add(maxId);
            map.remove(maxId);
        }
        return list1;
    }
    //获取所有博客的数目
    public long getBlogNum(){
        Long l = blogMapper.selectCount(null);
        return l;
    }
    //获取更新时间最近的n个推荐的博客
    public List<Blog> findTopRecommend(int n){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recommend",true)
                .orderByDesc("update_time");
        Page<Blog> page = new Page<>(1, n);
        List<Blog> records = blogMapper.selectPage(page, queryWrapper).getRecords();
        return records;
    }

    //获取最新的n个博客
    public List<Blog> findTopNew(int n){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("update_time");
        Page<Blog> page = new Page<>(1, n);
        List<Blog> records = blogMapper.selectPage(page, queryWrapper).getRecords();
        return records;
    }


    //根据模糊查询返回分页
    public List<Blog> findBlogByQuery(Page<Blog> page,String query){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title",query)
                .or()
                .like("content",query)
                .orderByDesc("update_time");
         blogMapper.selectPage(page, queryWrapper);
        List<Blog> records = page.getRecords();
        return records;
    }
    //获取模糊查询的博客数
    public long getBlogNumByQuery(String query){
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("title",query)
                .or()
                .like("content",query)
                .orderByDesc("update_time");
        long l = blogMapper.selectCount(queryWrapper);
        return l;
    }
}




