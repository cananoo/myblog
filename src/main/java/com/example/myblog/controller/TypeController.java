package com.example.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.pojo.Type;
import com.example.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@RequestParam(value = "pagenum",defaultValue = "1") String pagenum, Model model){  //设置页码默认为1
        //前端传来的均为字符串，需要字符类型转换
        Long l = Long.valueOf(pagenum);
        Page<Type> page = new Page<>(l,5);
        List<Type> records = typeService.findPage(page);;

        long current = page.getCurrent() ;
        long pagetotal = page.getPages();
        model.addAttribute("list",records)
                .addAttribute("current",current)
                .addAttribute("pagetotal",pagetotal);

        return "admin/typesmanage";
    }


}
