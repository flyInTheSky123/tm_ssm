package com.person.tm.service.impl;

import com.person.tm.mapper.ProductImageMapper;
import com.person.tm.pojo.ProductImage;
import com.person.tm.pojo.ProductImageExample;
import com.person.tm.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    ProductImageMapper productImageMapper;


    @Override
    public void add(ProductImage productImage) {
       productImageMapper.insertSelective(productImage);

    }

    @Override
    public void delete(int id) {
        productImageMapper.deleteByPrimaryKey(id);

    }

    //更新productimage
    @Override
    public void update(ProductImage productImage) {
        productImageMapper.updateByPrimaryKey(productImage);

    }

    //通过id查询一条数据
    @Override
    public ProductImage get(int id) {
        ProductImage productImage = productImageMapper.selectByPrimaryKey(id);
        return productImage;
    }

    //通过 pid（产品id),type(图片类型），查询数据
    @Override
    public List<ProductImage> list(int pid, String type) {
        ProductImageExample productImageExample = new ProductImageExample();
        productImageExample.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
        productImageExample.setOrderByClause("id desc");

        List<ProductImage> productImages = productImageMapper.selectByExample(productImageExample);

        return productImages;
    }

    //t
    @Override
    public List<ProductImage> list(int pid) {
        ProductImageExample productImageExample = new ProductImageExample();
        productImageExample.createCriteria().andPidEqualTo(pid);
        productImageExample.setOrderByClause("id desc");
        List<ProductImage> productImages = productImageMapper.selectByExample(productImageExample);

        return productImages;
    }

    //通过 产品id 获取所有的信息后，通过propertyvalue id进行删除该产品下的所有图片
    @Override
    public void deleteByPid(int pid) {
        List<ProductImage> list = list(pid);
        list.forEach(productImage -> {
            productImageMapper.deleteByPrimaryKey(productImage.getId());
        });

    }
}
