package com.example.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.pojo.Blog;
import com.example.myblog.pojo.Tag;
import com.example.myblog.pojo.Type;
import com.example.myblog.pojo.User;
import com.example.myblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    @Autowired
    private BlogTagsService blogTagsService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/tags")
    public String tags(@RequestParam(value = "current",defaultValue = "1") String current, @RequestParam(value = "id",defaultValue = "-1")String ide, Model model) {

        long l = Long.parseLong(String.valueOf(current));
        Page<Blog> page = new Page<>(l,2);
        List<Blog> list;
        long id = Long.parseLong(ide);
        if(id==-1){
            list = blogService.findBlogPage(page, null);
        }else{
            List<Long> blogIds = blogTagsService.findBlogsByTagId(id);

            list = blogService.findBlogsByIds(blogIds);

        }
        long c = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();

        List<Tag> tags = tagService.findAllTag();
        List<Type> types = typeService.findAllType();
        //获取博主信息
        User cananoo = userService.findUserByName("cananoo");


        //获取最新的n个博客
        List<Blog> topNew = blogService.findTopNew(4);

        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("tags",tags)
                .addAttribute("topNew",topNew)
                .addAttribute("cananoo",cananoo)
                .addAttribute("activeId",id)
                .addAttribute("user",cananoo)
                .addAttribute("types",types)
        ;

        return "tags";
    }

    @PostMapping("/tagsPage")
    public String pages(@RequestParam(value = "current",defaultValue = "1") String current,
                        @RequestParam(value = "activeId") String activeId, Model model) {

        long l = Long.parseLong(String.valueOf(current));
        Page<Blog> page = new Page<>(l,2);
        List<Blog> list;
        long id = Long.parseLong(activeId);
        if(id == -1){
            list = blogService.findBlogPage(page, null);
        }else{
            List<Long> blogIds = blogTagsService.findBlogsByTagId(id);
            list = blogService.findBlogsByIds(blogIds);
        }
        long c = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();

        List<Tag> tags = tagService.findAllTag();
        List<Type> types = typeService.findAllType();
        //获取博主信息
        User cananoo = userService.findUserByName("cananoo");
        //获取博客数
        long blogNum = blogService.getBlogNum();

        //获取最新的n个博客
        List<Blog> topNew = blogService.findTopNew(4);
        //获取博主信息
        User user = userService.findUserByName("cananoo");


        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("tags",tags)
                .addAttribute("topNew",topNew)
                .addAttribute("cananoo",cananoo)
                .addAttribute("activeId",id)
                .addAttribute("user",user)
                .addAttribute("types",types)
        ;

        return "tags :: blogBypages";
    }

}
