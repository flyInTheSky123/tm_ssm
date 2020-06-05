package com.person.tm.controller;


import com.person.tm.pojo.Admin;
import com.person.tm.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("")
public class AdminController {

    @Autowired
    AdminService adminService;

    //管理员登录
    @RequestMapping("loginForAdmin")
    public String loginAdmin(Admin admin, Model model, HttpSession session) {
        Admin a = adminService.get(admin.getName(), admin.getPassword());
        String str = "账号/密码错误，请重新输入";
        if (a == null) {
            model.addAttribute("msg", str);
            //返回页面，重新登录
            return "admin/login";
        }
        //登录成功，重定向到 管理员页面
        session.setAttribute("admin", a);
        return "redirect:admin_category_list";
    }
}
