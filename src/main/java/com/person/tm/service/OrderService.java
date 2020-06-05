package com.person.tm.service;

import com.person.tm.pojo.Order;
import com.person.tm.pojo.OrderItem;

import java.util.List;

//订单
public interface OrderService {
    String waitPay = "waitPay";          //等待付款
    String waitDelivery = "waitDelivery";//等待发货
    String waitConfirm = "waitConfirm";  //等待确认
    String waitReview = "waitReview";    //等待评价
    String finish = "finish";            //结束
    String delete = "delete";             //删除

    void add(Order c);

    float add(Order c, List<OrderItem> ois);

    void delete(int id);

    void update(Order c);

    Order get(int id);

    List list();

    List list(int uid, String status); //根据用户id 和订单状态查询数据库
}
