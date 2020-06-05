package com.person.tm.service.impl;

import com.person.tm.mapper.UserMapper;
import com.person.tm.pojo.User;
import com.person.tm.pojo.UserExample;
import com.person.tm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void add(User user) {
        userMapper.insertSelective(user);

    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);

    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);

    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> list() {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("id desc ");
        return userMapper.selectByExample(userExample);
    }


    //判断该 字段是否已经存在。
    @Override
    public boolean isExist(String name) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(name);

        List<User> users = userMapper.selectByExample(userExample);

        if (!users.isEmpty()){
            return  true;
        }

        return false;
    }

    //登录
    @Override
    public User get(String name, String password) {
        //通过name，password 进行获取数据库数据。
        UserExample example =new UserExample();
        example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
        List<User> result= userMapper.selectByExample(example);
        if(result.isEmpty())
            return null;
        return result.get(0);

    }
}
