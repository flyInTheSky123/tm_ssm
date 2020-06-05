package com.person.tm.service;

import com.person.tm.pojo.Property;

import java.util.List;
//属性
public interface PropertyService {

    //增
    void add(Property property);

    //删
    void delete(int id);

    //改
    void update(Property property);

    //通过ID 查找一条属性信息。
    Property get(int id);

    //通过分类id 查找相应的信息
    List<Property> list(int cid);

    //通过cid查询出该分类下有哪些属性，通过属性id，进行删除
    void deletByCid(int cid);

}
