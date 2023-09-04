package com.example.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.pojo.Type;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    //分类列表页
    @GetMapping("/types")
    public String types(@RequestParam(value = "current",defaultValue = "1") String current, Model model,HttpSession session){  //设置页码默认为1
        //前端传来的均为字符串，需要字符类型转换
        long l ;
        if (session.getAttribute("dpage") != null){
            l = Long.parseLong((String) session.getAttribute("dpage"));
            session.removeAttribute("dpage");
        }else {
            l = Long.parseLong(String.valueOf(current));
        }
        Page<Type> page = new Page<>(l,5);
        List<Type> list = typeService.findPage(page);
        long c = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();
        List<Type> allType = typeService.findAllType();
        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("types",allType);
        return "admin/typesmanage";
    }

    //局部刷新分类翻页
    @PostMapping("/types/pages")
    public String pages(@RequestParam(value = "current",defaultValue = "1") String current, Model model,HttpSession session){  //设置页码默认为1
        //前端传来的均为字符串，需要字符类型转换
        long l = Long.parseLong(String.valueOf(current));
        Page<Type> page = new Page<>(l,5);
        List<Type> list = typeService.findPage(page);
        long c = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();
        List<Type> allType = typeService.findAllType();
        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("types",allType);
        return "admin/typesmanage :: typelist";
    }


    //跳转到新增页面
    @GetMapping("/types/input")
    public  String input(){
        return  "admin/typeadd";
    }

    //跳转到修改页面 (共用页面)
    @GetMapping("/types/edit")
    public String edit(@RequestParam("id") String id,HttpSession session){
        session.setAttribute("id",id);
       return  "admin/typeadd";
    }

    //分类新增和修改
    @PostMapping("/types/save")
    public String saveAndEidtType(@RequestParam("typename") String typename, Model model, HttpSession session){
        String id = (String) session.getAttribute("id");
        Type typeByName = typeService.findTypeByName(typename);
        if (id != null){
            //修改
            if ( typeByName == null){
                int res = typeService.updateTypeById(Long.parseLong(id), typename);
                if (res == 1){
                    model.addAttribute("message","更新成功!");
                }else {
                    model.addAttribute("error","更新失败!");
                }
            }else {
                model.addAttribute("existed","类型已存在!");
            }
            session.removeAttribute("id");
        }else {
            //新增
            if ( typeByName == null){
                Type type = new Type();
                type.setName(typename);
                int i = typeService.saveType(type);
                if (i == 1){
                    model.addAttribute("message","添加成功!");
                }else {
                    model.addAttribute("error","添加失败!");
                }
            }else {
                model.addAttribute("existed","类型已存在!");
            }
        }
        return  "admin/typeadd :: addtype";

    }


    //删除分类
    @PostMapping("/types/delete")
    public  String deleteType(@RequestParam("deid") String deid,@RequestParam(value = "dpage",defaultValue = "1") String dpage, Model model,HttpSession session){
        System.out.println("当前页："+dpage);
        int i = typeService.deleteTypeById(Long.parseLong(deid));
        if (i == 1){
            model.addAttribute("message","删除成功!");
        }else {
           model.addAttribute("error","删除失败!");
        }

        long l = Long.parseLong(String.valueOf(dpage));
        Page<Type> page = new Page<>(l,5);
        List<Type> list = typeService.findPage(page);
        long c = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();
        List<Type> allType = typeService.findAllType();
        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("types",allType);

        return "admin/typesmanage :: typelist";
    }



}
