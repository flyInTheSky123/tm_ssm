package com.person.tm.mapper;

import com.person.tm.pojo.Order;
import com.person.tm.pojo.OrderExample;
import java.util.List;
//订单
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}