package com.person.tm.service.impl;

import com.person.tm.mapper.PropertyValueMapper;
import com.person.tm.pojo.*;
import com.person.tm.service.PropertyService;
import com.person.tm.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    PropertyValueMapper propertyValueMapper;

    @Autowired
    PropertyService propertyService;

    @Override
    public void init(Product p) {

        List<Property> pts = propertyService.list(p.getCid());

        for (Property pt: pts) {
            PropertyValue pv = get(pt.getId(),p.getId());
            if(null==pv){
                pv = new PropertyValue();
                pv.setPid(p.getId());
                pv.setPtid(pt.getId());
                propertyValueMapper.insert(pv);
            }
        }

    }

    @Override
    public void update(PropertyValue pv) {
        propertyValueMapper.updateByPrimaryKeySelective(pv);
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> pvs= propertyValueMapper.selectByExample(example);
        if (pvs.isEmpty())
            return null;
        return pvs.get(0);
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> result = propertyValueMapper.selectByExample(example);
        for (PropertyValue pv : result) {
            Property property = propertyService.get(pv.getPtid());
            pv.setProperty(property);
        }
        return result;
    }


    //获取该产品下的所有属性
    public List<PropertyValue> listByPid(int pid){
        //创建PropertyValueExample
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        //指定查询条件是pid。
        propertyValueExample.createCriteria().andPidEqualTo(pid);
        //通过pid（产品id），查询对应的propertyvalue 属性值
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(propertyValueExample);

        return propertyValues;
    }


    //通过产品id ，获取propertyValue 值，然后通过id进行删除该propertyvalue。
    public void deleteByPid(int pid){
        List<PropertyValue> propertyValues = listByPid(pid);
        propertyValues.forEach(propertyValue ->{
            propertyValueMapper.deleteByPrimaryKey(propertyValue.getId());
                }
        );

    }

}


