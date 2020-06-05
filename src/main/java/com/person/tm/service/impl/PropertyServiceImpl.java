package com.person.tm.service.impl;

import com.person.tm.mapper.PropertyMapper;
import com.person.tm.pojo.Property;
import com.person.tm.pojo.PropertyExample;
import com.person.tm.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//属性
@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    PropertyMapper propertyMapper;
    @Override
    public void add(Property property) {
        propertyMapper.insertSelective(property);

    }

    @Override
    public void delete(int id) {
        propertyMapper.deleteByPrimaryKey(id);

    }

    @Override
    public void update(Property property) {
        propertyMapper.updateByPrimaryKeySelective(property);

    }

    //通过Property id 查找属性
    @Override
    public Property get(int id) {

        return propertyMapper.selectByPrimaryKey(id);
    }

    //通过分类(Category) id 查找相应的属性
    @Override
    public List<Property> list(int cid) {
        PropertyExample propertyExample = new PropertyExample();
        propertyExample.createCriteria().andCidEqualTo(cid);
        propertyExample.setOrderByClause("id desc");

        return propertyMapper.selectByExample(propertyExample);
    }

    //通过cid查询出该分类下有哪些属性，通过属性id，进行删除
    @Override
    public void deletByCid(int cid) {
        List<Property> list = list(cid);
        list.forEach(property -> {
            propertyMapper.deleteByPrimaryKey(property.getId());
        });

    }
}
