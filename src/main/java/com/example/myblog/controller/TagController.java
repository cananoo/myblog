package com.example.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.pojo.Tag;
import com.example.myblog.service.TagService;
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
public class TagController {

    @Autowired
    private TagService tagService;

    //标签列表页
    @GetMapping("/tags")
    public String tags(@RequestParam(value = "current",defaultValue = "1") String current, Model model,HttpSession session){  //设置页码默认为1
        //前端传来的均为字符串，需要字符类型转换
        long l ;
        if (session.getAttribute("dpage") != null){
            l = Long.parseLong((String) session.getAttribute("dpage"));
            session.removeAttribute("dpage");
        }else {
            l = Long.parseLong(String.valueOf(current));
        }
        Page<Tag> page = new Page<>(l,5);
        List<Tag> list = tagService.findPage(page);
        long c = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();
        List<Tag> allTag = tagService.findAllTag();
        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("tags",allTag);
        return "admin/tagsmanage";
    }

    //局部刷新标签翻页
    @PostMapping("/tags/pages")
    public String pages(@RequestParam(value = "current",defaultValue = "1") String current, Model model,HttpSession session){  //设置页码默认为1
        //前端传来的均为字符串，需要字符类型转换
        long l = Long.parseLong(String.valueOf(current));
        Page<Tag> page = new Page<>(l,5);
        List<Tag> list = tagService.findPage(page);
        long c = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();
        List<Tag> allTag = tagService.findAllTag();
        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("tags",allTag);
        return "admin/tagsmanage :: taglist";
    }


    //跳转到新增页面
    @GetMapping("/tags/input")
    public  String input(){
        return  "admin/tagadd";
    }

    //跳转到修改页面 (共用页面)
    @GetMapping("tags/edit")
    public String edit(@RequestParam("id") String id,HttpSession session){
        session.setAttribute("id",id);
        return  "admin/tagadd";
    }

    //标签新增和修改
    @PostMapping("tags/save")
    public String saveAndEidtTag(@RequestParam("tagname") String tagname, Model model, HttpSession session){
        String id = (String) session.getAttribute("id");
        Tag tagByName = tagService.findTagByName(tagname);
        if (id != null){
            //修改
            if ( tagByName == null){
                int res = tagService.updateTagById(Long.parseLong(id), tagname);
                if (res == 1){
                    model.addAttribute("message","更新成功!");
                }else {
                    model.addAttribute("error","更新失败!");
                }
            }else {
                model.addAttribute("existed","标签已存在!");
            }
            session.removeAttribute("id");
        }else {
            //新增
            if ( tagByName == null){
                Tag tag = new Tag();
                tag.setName(tagname);
                int i = tagService.saveTag(tag);
                if (i == 1){
                    model.addAttribute("message","添加成功!");
                }else {
                    model.addAttribute("error","添加失败!");
                }
            }else {
                model.addAttribute("existed","标签已存在!");
            }
        }
        return  "admin/tagadd :: addtag";

    }


    //删除标签
    @PostMapping("/tags/delete")
    public  String deleteTag(@RequestParam("deid") String deid,@RequestParam(value = "dpage",defaultValue = "1") String dpage, Model model,HttpSession session){
        System.out.println("当前页："+dpage);
        int i = tagService.deleteTagById(Long.parseLong(deid));
        if (i == 1){
            model.addAttribute("message","删除成功!");
        }else {
            model.addAttribute("error","删除失败!");
        }

        long l = Long.parseLong(String.valueOf(dpage));
        Page<Tag> page = new Page<>(l,5);
        List<Tag> list = tagService.findPage(page);
        long c = page.getCurrent() ;
        long pagetotal = page.getPages();
        long size = page.getSize();
        List<Tag> allTag = tagService.findAllTag();
        model.addAttribute("list",list)
                .addAttribute("current",c)
                .addAttribute("pagetotal",pagetotal)
                .addAttribute("size",size)
                .addAttribute("tags",allTag);

        return "admin/tagsmanage :: taglist";
    }



}
