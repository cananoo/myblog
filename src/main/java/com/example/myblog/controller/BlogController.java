package com.example.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.pojo.Blog;
import com.example.myblog.pojo.Type;
import com.example.myblog.service.BlogService;
import com.example.myblog.service.TypeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    // 博客展示页面
    @GetMapping("/blogs")
    public String blogs(@RequestParam(value = "current",defaultValue = "1") String current, Model model, HttpSession session){
        long l ;
        if (session.getAttribute("bdpage") != null){
            l = Long.parseLong((String) session.getAttribute("bdpage"));
            session.removeAttribute("bdpage");
        }else {
            l = Long.parseLong(String.valueOf(current));
        }
        Page<Blog> page = new Page<>(l,5);
        List<Blog> list = blogService.findBlogPage(page, null);
        long c = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();
        List<Type> allType = typeService.findAllType();
        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("types",allType);
        return "admin/blogmanage";
    }

    //博客添加搜索条件
    @PostMapping("/blogs/search")
    public  String searchBlogs(@RequestParam(value = "current",defaultValue = "1") String current, Model model, HttpSession session,Blog blog){
        long l ;
        if (session.getAttribute("bdpage") != null){
            l = Long.parseLong((String) session.getAttribute("bdpage"));
            session.removeAttribute("bdpage");
        }else {
            l = Long.parseLong(String.valueOf(current));
        }
        Page<Blog> page = new Page<>(l,5);

        //如果没有选上推荐，就不传值(所有列表都展示)
        if (!blog.getRecommend()){
            blog.setRecommend(null);
        }

        List<Blog> list = blogService.findBlogPage(page, blog);
        long c = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();
        List<Type> allType = typeService.findAllType();
        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("types",allType);
        return  "admin/blogmanage :: bloglist";
    }

    // 跳转到新增页面
    @GetMapping("/blogs/toadd")
    public  String toAddBlog(Model model){
        List<Type> allType = typeService.findAllType();
        model.addAttribute("types",allType);
        return  "admin/blogpublish";
    }

}