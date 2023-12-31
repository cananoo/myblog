package com.example.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.pojo.*;
import com.example.myblog.service.BlogService;
import com.example.myblog.service.BlogTagsService;
import com.example.myblog.service.TagService;
import com.example.myblog.service.TypeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogTagsService blogTagsService;

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
        List<Tag> allTag = tagService.findAllTag();
        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("types",allType)
                .addAttribute("tags",allTag)
        ;
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
        List<Tag> allTag = tagService.findAllTag();
        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("types",allType)
                .addAttribute("tags",allTag);
        return  "admin/blogmanage :: bloglist";
    }

    // 跳转到新增页面
    @GetMapping("/blogs/toadd")
    public  String toAddBlog(Model model){
        List<Type> allType = typeService.findAllType();
        List<Tag> allTag = tagService.findAllTag();
        model.addAttribute("types",allType)
                .addAttribute("tags",allTag);
        return  "admin/blogpublish";
    }

    //跳转到修改页面 (共用页面)
    @GetMapping("/blogs/update")
    public String editBlog(@RequestParam("id") String id,HttpSession session,Model model){
        session.setAttribute("id",id);
        Blog blog = blogService.getBlogById(Long.parseLong(id));
        String ids = blogTagsService.getBlogTagsByBlogId(Long.parseLong(id));
        Long otypeId = blog.getTypeId();
        List<Type> allType = typeService.findAllType();
        List<Tag> allTag = tagService.findAllTag();
        model.addAttribute("types",allType)
                .addAttribute("tags",allTag)
                .addAttribute("blog",blog)
                .addAttribute("otypeId",otypeId)
                .addAttribute("ids",ids);
        return  "admin/blogpublish";
    }

   //新增和修改
    @PostMapping("/blogs/updateoradd")
    public String updateOrAddBlog(@RequestParam("tags")String tags, Blog blog, RedirectAttributes attributes,HttpSession session){
        User user = (User) session.getAttribute("user");
        blog.setUserId(user.getId());
        String id = (String) session.getAttribute("id");
        if (id != null){
            //先将原来的type的博客数-1
            Blog blog1 = blogService.getBlogById(Long.parseLong(id));
            Type type1 = typeService.getTypeById(blog1.getTypeId());
            type1.setBlogNum(type1.getBlogNum()-1);
            typeService.updateTypeBlogNumById(type1.getId(),type1.getBlogNum());
            //先将原来的tag的博客数-1
            String ids = blogTagsService.getBlogTagsByBlogId(Long.parseLong(id));
            String[] split1 = ids.split(",");
            for (int j = 0; j < split1.length; j++) {
                Tag tag = tagService.getTagById(Long.parseLong(split1[j]));
                tag.setBlogNum(tag.getBlogNum()-1);
                tagService.updateTagBlogNumById(tag.getId(),tag.getBlogNum());
            }
            //修改
            int i = blogService.updateBlogById(Long.parseLong(id), blog);
            blogTagsService.deleteByBlogId(Long.parseLong(id));
            String[] split = tags.split(",");
            //设置type的博客数+1
            Type type = typeService.getTypeById(blog.getTypeId());
            type.setBlogNum(type.getBlogNum()+1);
            typeService.updateTypeBlogNumById(type.getId(),type.getBlogNum());
            //设置tag的博客数+1
            for (int j = 0; j < split.length; j++) {
                Tag tag = tagService.getTagById(Long.parseLong(split[j]));
                tag.setBlogNum(tag.getBlogNum()+1);
                tagService.updateTagBlogNumById(tag.getId(),tag.getBlogNum());
            }
            //实现中间表更新
            for (int j = 0; j < split.length; j++) {
                BlogTags blogTags = new BlogTags();
                blogTags.setBlogsId(Long.parseLong(id));
                blogTags.setTagsId(Long.parseLong(split[j]));
                blogTagsService.saveBlogTags(blogTags);
            }

            if (i==1){
               attributes.addFlashAttribute("message","更新成功!");
            }else {
               attributes.addFlashAttribute("error","更新失败!");
            }
            //清除id 防止下次更新或者添加误判
            session.removeAttribute("id");
        }else {
            //新增
            String[] split = tags.split(",");
           //执行完此方法后，会自动主键回显
            int i = blogService.saveBlog(blog);
            //设置type的博客数+1
            Type type = typeService.getTypeById(blog.getTypeId());
            type.setBlogNum(type.getBlogNum()+1);
            typeService.updateTypeBlogNumById(type.getId(),type.getBlogNum());
            //设置tag的博客数+1
            for (int j = 0; j < split.length; j++) {
                Tag tag = tagService.getTagById(Long.parseLong(split[j]));
                tag.setBlogNum(tag.getBlogNum()+1);
                tagService.updateTagBlogNumById(tag.getId(),tag.getBlogNum());
            }

            //实现中间表更新
            for (int j = 0; j < split.length; j++) {
                BlogTags blogTags = new BlogTags();
                blogTags.setBlogsId(blog.getId());
                blogTags.setTagsId(Long.parseLong(split[j]));
                blogTagsService.saveBlogTags(blogTags);
            }
            if (i==1){
                attributes.addFlashAttribute("message","添加成功!");
            }else {
                attributes.addFlashAttribute("error","添加失败!");
            }
        }

   return  "redirect:/admin/blogs";
    }
    //删除
    @GetMapping("/blogs/delete")
    public String deleteBlog(@RequestParam("id") String id,RedirectAttributes attributes){
        //设置type的博客数-1
        Blog blog = blogService.getBlogById(Long.parseLong(id));
        Type type = typeService.getTypeById(blog.getTypeId());
        type.setBlogNum(type.getBlogNum()-1);
        typeService.updateTypeBlogNumById(type.getId(),type.getBlogNum());
        //设置tag的博客数-1
        String ids = blogTagsService.getBlogTagsByBlogId(Long.parseLong(id));
        String[] split = ids.split(",");
        for (int j = 0; j < split.length; j++) {
            Tag tag = tagService.getTagById(Long.parseLong(split[j]));
            tag.setBlogNum(tag.getBlogNum()-1);
            tagService.updateTagBlogNumById(tag.getId(),tag.getBlogNum());
        }
        //先删除中间表，再删除博客
        blogTagsService.deleteByBlogId(Long.parseLong(id));
        int i = blogService.deleteBlogById(Long.parseLong(id));
        if (i==1){
            attributes.addFlashAttribute("message","删除成功!");
        }else {
            attributes.addFlashAttribute("error","删除失败!");
        }
        return  "redirect:/admin/blogs";
    }

}