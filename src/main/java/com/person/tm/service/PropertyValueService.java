package com.person.tm.service;

import com.person.tm.pojo.Product;
import com.person.tm.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService  {

    //初始化，当property 表 所对应的propertyvalue 表 里面的数据不存在时，创建数据。
    void init(Product p);

    //更新propertyvalue 表
    void update(PropertyValue propertyValue);

    //通过ptid（property 的id），pid（产品id），查询数据
    PropertyValue get(int ptid,int pid);

    //通过pid（产品id），进行查询属性值。
    List<PropertyValue> list(int pid);


    //通过产品id 进行获取。
    List<PropertyValue> listByPid(int pid);

    //通过产品id，先获取propertyvalue数据。再通过id进行删除
    public void deleteByPid(int pid);

}
