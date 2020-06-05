package com.person.tm.service.impl;

import com.person.tm.mapper.OrderMapper;
import com.person.tm.pojo.Order;
import com.person.tm.pojo.OrderExample;
import com.person.tm.pojo.OrderItem;
import com.person.tm.pojo.User;
import com.person.tm.service.OrderItemService;
import com.person.tm.service.OrderService;
import com.person.tm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserService userService;

    @Autowired
    OrderItemService orderItemService;


    @Override
    public void add(Order o) {
        orderMapper.insertSelective(o);

    }


    /**
     * 当前端点击"提交订单"时：
     * 1，创建order订单
     * 2，修改orderItem 表。
     * 3，当其中一个有异常时，事务会自动进行回滚。
     *
     * @param o
     * @param ois
     * @return
     */
    @Override   //propagation.required :如果存在一个事务，则支持当前事务，没有事务则开启。
    // rollbackForClassName="Exception" :遇到exception或者error时，则回滚，避免脏数据
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public float add(Order o, List<OrderItem> ois) {
        float total = 0;
        //创建订单
        add(o);
        for (OrderItem oi : ois) {
            //为orderItem 设置订单id
            oi.setOid(o.getId());
            //更新orderitem
            orderItemService.update(oi);
            //获取总金额
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
        }
        return total;
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);

    }

    @Override
    public void update(Order o) {
        orderMapper.updateByPrimaryKeySelective(o);

    }

    @Override
    public Order get(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);

        return order;
    }

    @Override
    public List list() {
        OrderExample orderExample = new OrderExample();
        orderExample.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(orderExample);
        //设置用户。
        setUser(orders);
        return orders;
    }

    ////根据用户id 和订单状态查询数据库(注意*这里是andStatusNotEqualTo(status))
    @Override
    public List list(int uid, String status) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(status);
        orderExample.setOrderByClause("id desc");

        List<Order> orders = orderMapper.selectByExample(orderExample);
        return orders;
    }


    //setUser() 为每一个订单 设置 用户
    void setUser(List<Order> os) {
        for (Order o : os) {
            setUser(o);
        }
    }

    void setUser(Order o) {
        Integer uid = o.getUid();
        User user = userService.get(uid);
        o.setUser(user);

    }
}
