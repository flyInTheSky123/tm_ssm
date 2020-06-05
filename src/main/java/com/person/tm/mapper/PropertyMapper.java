package com.person.tm.mapper;

import com.person.tm.pojo.Property;
import com.person.tm.pojo.PropertyExample;
import java.util.List;
//属性
public interface PropertyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Property record);

    int insertSelective(Property record);

    List<Property> selectByExample(PropertyExample example);

    Property selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Property record);

    int updateByPrimaryKey(Property record);
}