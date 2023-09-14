package com.example.myblog.controller;

import com.example.myblog.pojo.Blog;
import com.example.myblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ArchiveController {

    @Autowired
    private BlogService blogService;


    @GetMapping("/archives")
    public String archives(Model model){

        Map<String, List<Blog>> blogsByYear = blogService.findBlogsByYear();
        long blogNum = blogService.getBlogNum();
        List<Blog> topNew = blogService.findTopNew(4);
        model.addAttribute("blogsByYear",blogsByYear);
        model.addAttribute("blogNum",blogNum);
        model.addAttribute("topNew",topNew);

        return "archives";
    }



}
