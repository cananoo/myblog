package com.example.myblog.controller;

import com.example.myblog.pojo.Comment;
import com.example.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CommentController {

 @Autowired
 private CommentService commentService;

    @GetMapping("/comments/{blogId}")
    public String Comments(@PathVariable("blogId") Long blogId, Model model){
        model.addAttribute("comments",commentService.getComment(blogId));
        model.addAttribute("childrenComment",commentService.getChidrenComment(commentService.getComment(blogId)));
        model.addAttribute("allComment",commentService.getAllComment());
        return "blog :: commentList";
    }


    @PostMapping("/comments/save")
    public String post(Comment comment){
        commentService.saveComment(comment);
        return "redirect:/comments/" + comment.getBlogId();
    }
}
