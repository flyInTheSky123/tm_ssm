package com.person.tm.mapper;

import com.person.tm.pojo.Category;
import com.person.tm.pojo.CategoryExample;
import java.util.List;

//分类
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}