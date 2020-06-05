package com.person.tm.service;

import com.person.tm.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {

    //图片类型是 单个图片
    String type_single="type_single";

    //图片类型是 详情图片
    String type_detail="type_detail";

    //增加
    void add(ProductImage productImage);

    //删除
    void delete(int id);

    //改
    void update(ProductImage productImage);

    //通过id 获取数据
    ProductImage get(int id);

    //通过 pid和 type，获取数据
    List<ProductImage> list(int pid,String type);

    //通过pid 获取该产品所有的图片信息；
    List<ProductImage> list(int pid);

    //通过 产品id 获取所有的信息后，通过propertyvalue id进行删除该产品下的所有图片
    public void deleteByPid(int id);


}
