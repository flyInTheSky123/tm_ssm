package com.person.tm.mapper;

import com.person.tm.pojo.Product;
import com.person.tm.pojo.ProductExample;
import java.util.List;
//产品
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);


}