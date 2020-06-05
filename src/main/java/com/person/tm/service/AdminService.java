package com.person.tm.service;

import com.person.tm.pojo.Admin;


import java.util.List;

//管理员
public interface AdminService {

    //
    void add(Admin user);

    void delete(int id);

    void update(Admin user);

    Admin get(int id);

    Admin get(String name, String password);
}
