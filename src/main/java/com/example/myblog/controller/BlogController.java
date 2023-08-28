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

    @GetMapping("/blogs")
    public String blogs(@RequestParam(value = "blognum",defaultValue = "1") String blognum,Model model, HttpSession session,Blog blog){
        long l ;
        if (session.getAttribute("bdpage") != null){
            l = Long.parseLong((String) session.getAttribute("bdpage"));
            session.removeAttribute("bdpage");
        }else {
            l = Long.parseLong(blognum);
        }
        Page<Blog> page = new Page<>(l,5);
        List<Blog> list = blogService.findBlogPage(page, blog);
        long current = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();

        List<Type> allType = typeService.findAllType();

        model.addAttribute("list",list)
                .addAttribute("current",current)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("types",allType);
        return "admin/blogmanage";
    }
}
