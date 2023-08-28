package com.example.myblog.controller;

import com.example.myblog.pojo.User;
import com.example.myblog.service.UserService;
import com.example.myblog.util.MD5Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin") //这里的路径是相对于localhost:8080的
public class LoginController {

    @Autowired
    private UserService userService;

    //登录页
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }


    //登录进入管理首页
    @PostMapping("/login")
    public String login(String username, String password, HttpSession session, RedirectAttributes attributes, Model model){
        User user = userService.checkUser(username, MD5Utils.code(password));
        if (user != null){
            user.setPassword(null); // 不要把密码传到前端(不安全)
        session.setAttribute("user",user);
        return "admin/index";
        }else {
            attributes.addFlashAttribute("message","用户名或密码错误"); // 重定向传递参数
            return "redirect:/admin";
        }
    }

    //注销操作
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
