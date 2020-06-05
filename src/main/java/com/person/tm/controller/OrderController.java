package com.person.tm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.person.tm.pojo.Order;
import com.person.tm.service.OrderItemService;
import com.person.tm.service.OrderService;
import com.person.tm.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;


    //显示所有订单
    @RequestMapping("admin_order_list")
    public String list(Model model, Page page){
        //为分页设置参数  当前起始位置，每页条数。
        PageHelper.offsetPage(page.getStart(),page.getCount());

        //获取所有的订单记录，同时在list() 里面设置用户
        List<Order> os= orderService.list();

        //获取记录总条数
        int total = (int) new PageInfo<>(os).getTotal();
        //为Page 设置总数
        page.setTotal(total);

        //调用orderItemService ，将order 与orderItem 进行一一对应，同时设置订单项总数，总金额，设置订单项里面的产品
        orderItemService.fill(os);

        model.addAttribute("os", os);
        model.addAttribute("page", page);

        return "admin/listOrder";
    }


    //发货
    @RequestMapping("admin_order_delivery")
    public String delivery(Order o) throws IOException {
        //设置发货时间
        o.setDeliveryDate(new Date());
        //设置订单 状态 为"等待确认"
        o.setStatus(OrderService.waitConfirm);
        //更新数据库
        orderService.update(o);
        return "redirect:admin_order_list";
    }
}
