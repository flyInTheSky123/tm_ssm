package com.person.tm.service.impl;

import com.person.tm.mapper.CategoryMapper;
import com.person.tm.mapper.ProductImageMapper;
import com.person.tm.mapper.ProductMapper;
import com.person.tm.pojo.Category;
import com.person.tm.pojo.Product;
import com.person.tm.pojo.ProductExample;
import com.person.tm.pojo.ProductImage;
import com.person.tm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ReviewService reviewService;

    @Override
    public void add(Product product) {
        productMapper.insertSelective(product);


    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);

    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);

    }


    // get和list方法都会把取出来的Product对象上设置对应的category
    @Override
    public Product get(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        setCategory(product);
        //设置相应的图片
        setProductFirstImage(product);
        return product;
    }


    //为了减少ProductController 里面通过 cid 获取category 代码的重复使用。
    void setCategory(List<Product> ps) {
        for (Product p : ps)
            setCategory(p);
    }

    void setCategory(Product product) {
        Integer cid = product.getCid();
        Category category = categoryMapper.selectByPrimaryKey(cid);
        product.setCategory(category);
    }


    //查询该 cid 下的所有产品。
    @Override
    public List<Product> list(int cid) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andCidEqualTo(cid);
        productExample.setOrderByClause("id desc");
        List<Product> products = productMapper.selectByExample(productExample);
        //为该产品设置分类信息。
        setCategory(products);
        //设置所有产品的图片；
        setProductFirstImage(products);
        return products;
    }

    @Override
    public void setProductFirstImage(Product p) {
        //通过产品id 在productimage数据库中 查询数据
        List<ProductImage> list = productImageService.list(p.getId(), productImageService.type_single);

        //判断是否为空
        if (!list.isEmpty()) {

            // System.out.println("不为空---------");
            //将查询出的第一个图片作为产品管理里面的图片（第一图片）
            ProductImage productImage = list.get(0);
            //为该产品设置第一图片。
            p.setProductFirstImage(productImage);
        }

    }

    //接收传入的集合参数
    public void setProductFirstImage(List<Product> ps) {
        //对集合的参数进行遍历
        for (Product p : ps) {
            //调用方法setProductFirstImage，设置图片
            setProductFirstImage(p);
        }
    }


    @Override
    public void fill(List<Category> categories) {
        for (Category c : categories) {
            fill(c);
        }

    }

    @Override
    public void fill(Category category) {
        //通过分类id 获取对应的 产品集合
        List<Product> list = list(category.getId());
        category.setProducts(list);

    }


    //为每个分类的每行产品准备8个推荐分类。
    @Override
    public void fillByRow(List<Category> categories) {
        //定义每行8个产品数量
        int productNumberEachRow = 8;

        for (Category c : categories) {
            //该分类有多少个产品
            List<Product> products = c.getProducts();

            //定义每行的产品
            List<List<Product>> productsByRow = new ArrayList<>();

            for (int i = 0; i < products.size(); i += productNumberEachRow) {

                //用于控制每行最多只有8 个产品
                int size = i + productNumberEachRow;
                //size=products.size()>size?size :products.size();
                size = size > products.size() ? products.size() : size;
                //将数量为size的产品放入productsByRow。（表示该行有哪些产品）
                List<Product> ps = products.subList(i, size);
                productsByRow.add(ps);

            }

            //当该分类下的每行都有指定数量的产品时,装入该分类的setProductsByRow中。
            c.setProductsByRow(productsByRow);


        }


    }

    @Override
    public void setSaleAndReviewNumber(List<Product> ps) {
        for (Product p : ps) {
            setSaleAndReviewNumber(p);
        }


    }

    @Override
    public List<Product> search(String name) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andNameLike("%" + name + "%");
        productExample.setOrderByClause("id desc");
        List<Product> products = productMapper.selectByExample(productExample);
        setProductFirstImage(products);
        setCategory(products);
        return products;
    }

    //设置销售量 和评论量 的总数
    @Override
    public void setSaleAndReviewNumber(Product p) {
        int saleCount = orderItemService.getSaleCount(p.getId());
        p.setSaleCount(saleCount);

        int count = reviewService.getCount(p.getId());
        p.setReviewCount(count);


    }


}
