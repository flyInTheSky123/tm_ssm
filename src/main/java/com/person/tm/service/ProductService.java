package com.person.tm.service;

import com.person.tm.pojo.Category;
import com.person.tm.pojo.Product;

import java.util.List;

//产品
public interface ProductService {
    //增加
    void add(Product product);

    //删除
    void delete(int id);

    //更新
    void update(Product product);

    //通过产品id 获取 相应的数据
    Product get(int id);

    //通过分类的id 获取 该分类下的所有产品
    List<Product> list(int cid);

    void setProductFirstImage(Product p);


    //--------------------- 前端页面--------------------
    //为右边显示产品分类。
    //为分类填充 产品
    void fill(Category category);

    //为多个分类填充 产品集合
    void fill(List<Category> categories);

    //为多个分类填充 推荐产品集合，即把产品集合 分成多行，每一行产品个数是指定个数
    void fillByRow(List<Category> categories);

    //获取单个产品的数据
    void setSaleAndReviewNumber(Product p);

    void setSaleAndReviewNumber(List<Product> ps);

    //通过关键字进行模糊查询
    List<Product> search(String name);

}
