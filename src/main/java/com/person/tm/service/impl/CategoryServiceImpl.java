package com.person.tm.service.impl;

import com.person.tm.mapper.CategoryMapper;
import com.person.tm.pojo.Category;
import com.person.tm.pojo.CategoryExample;
import com.person.tm.service.CategoryService;
import com.person.tm.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl  implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

//    @Override
//    public int total() {
//        return categoryMapper.total();
//    }

    public List<Category> list(){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("id desc");
        return categoryMapper.selectByExample(categoryExample);
    }



    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public void delete(Category category) {
        categoryMapper.deleteByPrimaryKey(category.getId());
    }

    @Override
    public Category get(int id) {

        Category c = categoryMapper.selectByPrimaryKey(id);
        return  c;

    }

    @Override
    public void update(Category category) {
    categoryMapper.updateByPrimaryKeySelective(category);
    }


}