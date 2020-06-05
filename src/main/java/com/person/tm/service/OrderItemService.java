package com.person.tm.service;

import com.person.tm.pojo.Order;
import com.person.tm.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {

    //crud

    void add(OrderItem orderItem);

    void delete(int id);

    void update(OrderItem orderItem);

    OrderItem get(int id);

    List list();

    void fill(Order order);

    void fill(List<Order> os);

    //----------------------前端页面显示单个产品所需要的数据
    int getSaleCount(int pid);

    //查询该uid 用户的购物车里面的产品（还没有生成订单）。
    List<OrderItem> listByUser(int uid);

    //通过订单oid ，查询订单项
    List<OrderItem> listByOid(int oid);

    //通过订单项id删除指定的订单项（订单项即购物车）
    String deleteByOrderItemId(Integer orderItemId);

}
