package com.person.tm.pojo;

import java.util.List;

public class Category {
    private Integer id;

    private String name;

    //非数据库字段
    //前端页面：一个分类对应多个产品
    private List<Product> products;

    /*前端页面：List<List<Product>> ---》 一个分类多行产品列表，每一个列表又有多个产品  */
    private List<List<Product>> productsByRow;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}