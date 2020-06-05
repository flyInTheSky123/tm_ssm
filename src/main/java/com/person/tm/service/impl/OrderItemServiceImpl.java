package com.person.tm.service.impl;

import com.person.tm.mapper.OrderItemMapper;
import com.person.tm.pojo.Order;
import com.person.tm.pojo.OrderItem;
import com.person.tm.pojo.OrderItemExample;
import com.person.tm.pojo.Product;
import com.person.tm.service.OrderItemService;
import com.person.tm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    ProductService productService;

    @Override
    public void add(OrderItem orderItem) {

        orderItemMapper.insertSelective(orderItem);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);

    }

    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKeySelective(orderItem);

    }

    @Override
    public OrderItem get(int id) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
        setProduct(orderItem);
        return orderItem;
    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);


        return orderItems;
    }

    //fill(),填充 订单所对应的订单项（一对多）的信息
    @Override
    public void fill(List<Order> os) {
        for (Order o : os) {
            fill(o);
        }

    }

    @Override
    public void fill(Order order) {
//        在fill(Order order)中：
//        1. 根据订单id查询出其对应的所有订单项
//        2. 通过setProduct为所有的订单项设置Product属性
//        3. 遍历所有的订单项，然后计算出该订单的总金额和总数量
//        4. 最后再把订单项设置在订单的orderItems属性上。
        //创建example.
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andOidEqualTo(order.getId());
        orderItemExample.setOrderByClause("id desc");
        //通过oid（订单id），查询订单项。（一对多）
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        setProduct(orderItems);

        float total = 0;   //总金额
        int totalNum = 0;  //总数量

        //遍历订单项
        for (OrderItem oi : orderItems) {
            //总价格。
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
            //总数量
            totalNum += oi.getNumber();

        }
        //把订单项 设置在订单的orderItems上
        order.setOrderItems(orderItems);
        //设置总金额
        order.setTotal(total);
        //设置总数量
        order.setTotalNumber(totalNum);


    }


    //获取产品的销售量
    @Override
    public int getSaleCount(int pid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andPidEqualTo(pid);
        orderItemExample.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        int count = 0;
        for (OrderItem oi : orderItems) {
            count += oi.getNumber();
        }
        return count;
    }


    //通过uid 和 oid==null，进行查询该用户下的购物车里面订单项（oid==null说明还没有生成订单，一直在购物车中）
    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        setProduct(orderItems);
        return orderItems;
    }

    //通过oid 获取订单项
    @Override
    public List<OrderItem> listByOid(int oid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andOidEqualTo(oid);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        //为每个订单项设置它的产品
        setProduct(orderItems);
        return orderItems;
    }

    //通过订单项id和用户id 删除指定的订单项（订单项即购物车）
    @Override
    public String deleteByOrderItemId(Integer orderItemId) {
        //删除指定订单项
        int i = orderItemMapper.deleteByPrimaryKey(orderItemId);
        //如果返回值是success ，则删除成功
        if (i > 0) {
            return "success";
        }
        return "false";
    }


    //setProduct(),设置订单项 里面的Product属性。
    void setProduct(List<OrderItem> ois) {

        for (OrderItem oi : ois) {
            setProduct(oi);

        }
    }

    void setProduct(OrderItem oi) {
        //productService.get() 里面可以设置图片。
        Product product = productService.get(oi.getPid());
        oi.setProduct(product);


    }
}
