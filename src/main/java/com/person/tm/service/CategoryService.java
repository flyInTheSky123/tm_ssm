package com.person.tm.service;

import com.person.tm.pojo.Category;
import com.person.tm.util.Page;

import java.util.List;

public interface CategoryService{
//    //得到多少条数据
//    int total();
//    //在分页的作用下，得到数据
//    List<Category> list(Page page);

    List<Category> list();


    //向category中插入新的分类
    void add(Category category);

    //通过id 删除
    void delete(Category category);

    //通过id 获取catgory
    Category get(int id);

    //通过id 修改category
    void update(Category category);

}