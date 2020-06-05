package com.person.tm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.person.tm.pojo.User;
import com.person.tm.service.UserService;
import com.person.tm.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    UserService userService;

    //查找所有的用户
    @RequestMapping("admin_user_list")
    public String list(Model model, Page page){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<User> list = userService.list();
        int total = (int) new PageInfo<>(list).getTotal();
        page.setTotal(total);

        model.addAttribute("page",page);
        model.addAttribute("us",list);

        return "admin/listUser";
    }
}
