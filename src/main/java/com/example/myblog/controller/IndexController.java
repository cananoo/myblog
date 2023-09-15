package com.example.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.pojo.*;
import com.example.myblog.service.*;
import com.example.myblog.util.MarkDownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogTagsService blogTagsService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(@RequestParam(value = "current",defaultValue = "1") String current, Model model) {
        long l = Long.parseLong(String.valueOf(current));
        Page<Blog> page = new Page<>(l,5);
        List<Blog> list = blogService.findBlogPage(page, null);
        long c = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();
        List<Long> topType = blogService.findTopType(6);
        //根据list返回Type的集合
        List<Type> types = typeService.findTypeByList(topType);

        List<Long> topTag = blogTagsService.findTopTags(5);
        List<Tag> tags = tagService.findTagByList(topTag);
        //获取博主信息
        User cananoo = userService.findUserByName("cananoo");

        //获取博客数
        long blogNum = blogService.getBlogNum();

        //获取最新推荐的n个博客
        List<Blog> topRecommend = blogService.findTopRecommend(5);

        //获取最新的n个博客
        List<Blog> topNew = blogService.findTopNew(4);

        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("types",types)
                .addAttribute("tags",tags)
                .addAttribute("user",cananoo)
                .addAttribute("blogNum",blogNum)
                .addAttribute("topRecommend",topRecommend)
                .addAttribute("topNew",topNew)
        ;
        return "index";
    }

    //局部刷新分类翻页
    @PostMapping("/pages")
    public String pages(@RequestParam(value = "current",defaultValue = "1") String current, Model model){  //设置页码默认为1
        //前端传来的均为字符串，需要字符类型转换
        long l = Long.parseLong(String.valueOf(current));
        Page<Blog> page = new Page<>(l,5);
        List<Blog> list = blogService.findBlogPage(page, null);
        long c = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();
        List<Long> topType = blogService.findTopType(6);
        //根据list返回Type的集合
        List<Type> types = typeService.findTypeByList(topType);

        List<Long> topTag = blogTagsService.findTopTags(5);
        List<Tag> tags = tagService.findTagByList(topTag);
        User cananoo = userService.findUserByName("cananoo");

        //获取博客数
        long blogNum = blogService.getBlogNum();

        //获取最新推荐的n个博客
        List<Blog> topRecommend = blogService.findTopRecommend(5);

        //获取最新的n个博客
        List<Blog> topNew = blogService.findTopNew(4);

        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("types",types)
                .addAttribute("tags",tags)
                .addAttribute("user",cananoo)
                .addAttribute("blogNum",blogNum)
                .addAttribute("topRecommend",topRecommend)
                .addAttribute("topNew",topNew)
        ;
        return "index::findex";
    }

    //全局搜索
    @PostMapping("/search")
    public String search(@RequestParam(value = "current",defaultValue = "1") String current,
                         @RequestParam(value = "query",defaultValue = "") String query,
                         Model model){
        long l = Long.parseLong(String.valueOf(current));
        Page<Blog> page = new Page<>(l,5);
        List<Blog> list = blogService.findBlogByQuery(page,query);
        long blogNum = blogService.getBlogNumByQuery(query);
        long c = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();

        User cananoo = userService.findUserByName("cananoo");

        //获取最新的n个博客
        List<Blog> topNew = blogService.findTopNew(4);

        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("user",cananoo)
                .addAttribute("topNew",topNew)
                .addAttribute("blogNum",blogNum)
        ;
        return "search";
    }

    //局部刷新搜索翻页
    @PostMapping("/searchpages")
    public String searchpages(@RequestParam(value = "current",defaultValue = "1") String current,
                         @RequestParam(value = "query",defaultValue = "") String query,
                         Model model){
        long l = Long.parseLong(String.valueOf(current));
        Page<Blog> page = new Page<>(l,5);
        List<Blog> list = blogService.findBlogByQuery(page,query);
        long blogNum = blogService.getBlogNumByQuery(query);
        long c = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();

        User cananoo = userService.findUserByName("cananoo");

        //获取最新的n个博客
        List<Blog> topNew = blogService.findTopNew(4);

        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("user",cananoo)
                .addAttribute("topNew",topNew)
                .addAttribute("blogNum",blogNum)
        ;
        return "search::fsearch";
    }


 //博客详情页展示
    @GetMapping("/blog")
    public String blog(@RequestParam(value = "id") String id,Model model){
        long l = Long.parseLong(String.valueOf(id));
        Blog blog = blogService.getBlogById(l);


        //博客浏览次数+1
        blogService.updateViews(l);

        User user = userService.findUserByName("cananoo");

        String tags = blogTagsService.getBlogTagsByBlogId(l);
        //将字符串转换为关于id的list
        String[] split = tags.split(",");
        List<Long> list = new ArrayList<>();
        for (String s : split) {
            list.add(Long.parseLong(s));
        }
        //获取最新的n个博客
        List<Blog> topNew = blogService.findTopNew(4);
        List<Tag> tagList = tagService.findTagByList(list);
        //Blog newBlog = new Blog();
        //将blog的属性复制到newBlog中
        //BeanUtils.copyProperties(blog,newBlog);
        //将markdown格式转换为html格式

        //先将content"##### 正文:"后的内容进行截取
        int i1 = blog.getContent().indexOf("##### 正文:");
        String substring = blog.getContent().substring(i1 + 11);
        String content = MarkDownUtils.markdownToHtmlExtensions(substring);
        blog.setContent(content);

        //获取博客顶级评论
        List<Comment> comments = commentService.getComment(l);

        //获取博客的子评论
        Map<Long, List<Comment>> chidrenComment = commentService.getChidrenComment(comments);

          //获取所有评论
        List<Comment> allComment = commentService.getAllComment();
        model.addAttribute("blog",blog)
                .addAttribute("user",user)
                .addAttribute("tags",tagList)
                .addAttribute("topNew",topNew)
                .addAttribute("comments",comments)
                .addAttribute("childrenComment",chidrenComment)
                .addAttribute("allComment",allComment)

        ;
      return "blog";
    }

    //跳转到关于我页面
    @GetMapping("/about")
    public String about(Model model){
        List<Blog> topNew = blogService.findTopNew(4);

        model.addAttribute("topNew",topNew);

        return "about";
    }

}
