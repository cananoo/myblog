package com.example.myblog.controller;

import com.example.myblog.exception.UserTooManyException;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @GetMapping("/{id}/{name}")
    public String index(@PathVariable("id") Integer id,@PathVariable("name") String name) { //SpringMVC支持路径中的占位符

        System.out.println("index");
        return "index";
    }
}
