package com.person.tm.service;

import com.person.tm.pojo.User;

import java.util.List;

//用户
public interface UserService {

    //
    void add(User user);

    void delete(int id);

    void update(User user);

    User get(int id);

    List<User> list();

//-------------前端数据。
    //判断某名称是否使用过。
    boolean isExist(String name);

    User get(String name,String password);
}
