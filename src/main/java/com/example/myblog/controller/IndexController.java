package com.example.myblog.controller;

import com.example.myblog.exception.UserTooManyException;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        if (true){
            throw  new UserTooManyException("博客找不到");
        }
        return "index";
    }
}
