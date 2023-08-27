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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //分类列表页
    @GetMapping("/types")
    public String types(@RequestParam(value = "pagenum",defaultValue = "1") String pagenum, Model model,HttpSession session){  //设置页码默认为1
        //前端传来的均为字符串，需要字符类型转换
        long l ;
        if (session.getAttribute("dpage") != null){
             l = Long.parseLong((String) session.getAttribute("dpage"));
            session.removeAttribute("dpage");
        }else {
             l = Long.parseLong(pagenum);
        }
        Page<Type> page = new Page<>(l,5);
        List<Type> records = typeService.findPage(page);;

        long current = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();
        model.addAttribute("list",records)
                .addAttribute("current",current)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size);

        return "admin/typesmanage";
    }

    //跳转到新增页面
    @GetMapping("/types/input")
    public  String input(){
        return  "/admin/typeadd";
    }

    //跳转到修改页面 (共用页面)
    @GetMapping("types/edit")
    public String edit(@RequestParam("id") String id,HttpSession session){
        session.setAttribute("id",id);
       return  "/admin/typeadd";
    }

    //分类新增和修改
    @PostMapping("types/save")
    public String saveAndEidtType(@RequestParam("typename") String typename, RedirectAttributes attributes, HttpSession session){
        String id = (String) session.getAttribute("id");
        System.out.println(id);
        Type typeByName = typeService.findTypeByName(typename);
        if (id != null){
            //修改
            if ( typeByName == null){
                int res = typeService.updateTypeById(Long.parseLong(id), typename);
                if (res == 1){
                    attributes.addFlashAttribute("message","更新成功!");
                }else {
                    attributes.addFlashAttribute("error","更新失败!");
                }
            }else {
                attributes.addFlashAttribute("existed","类型已存在!");
            }
        }else {
            //新增
            if ( typeByName == null){
                Type type = new Type();
                type.setName(typename);
                int i = typeService.saveType(type);
                if (i == 1){
                    attributes.addFlashAttribute("message","添加成功!");
                }else {
                    attributes.addFlashAttribute("error","添加失败!");
                }
            }else {
                attributes.addFlashAttribute("existed","类型已存在!");
            }
        }
        return  "redirect:/admin/types/input";

    }


    //删除分类
    @GetMapping("/types/delete")
    public  String deleteType(@RequestParam("id") String id,@RequestParam("dpage") String dpage, RedirectAttributes attributes,HttpSession session){
        int i = typeService.deleteTypeById(Long.parseLong(id));
        if (i == 1){
            attributes.addFlashAttribute("message","删除成功!");
        }else {
            attributes.addFlashAttribute("error","删除失败!");
        }
        //实现删除页面跳转到同一页面可以继续删除
        session.setAttribute("dpage",dpage);
        return  "redirect:/admin/types";
    }



}
