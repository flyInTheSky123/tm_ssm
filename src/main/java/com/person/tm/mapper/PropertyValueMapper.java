package com.person.tm.mapper;

import com.person.tm.pojo.PropertyValue;
import com.person.tm.pojo.PropertyValueExample;
import java.util.List;
//属性值
public interface PropertyValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValue record);

    int insertSelective(PropertyValue record);

    List<PropertyValue> selectByExample(PropertyValueExample example);

    PropertyValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyValue record);

    int updateByPrimaryKey(PropertyValue record);


}