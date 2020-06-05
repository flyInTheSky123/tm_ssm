package com.person.tm.service.impl;

import com.person.tm.mapper.AdminMapper;
import com.person.tm.pojo.Admin;
import com.person.tm.pojo.AdminExample;
import com.person.tm.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//管理员
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public void add(Admin admin){
        adminMapper.insertSelective(admin);

    }


    @Override
    public void delete(int id) {
        adminMapper.deleteByPrimaryKey(id);

    }

    @Override
    public void update(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public Admin get(int id) {
        return  adminMapper.selectByPrimaryKey(id);
    }
    //登录
    @Override
    public Admin get(String name, String password) {
        //通过name，password 进行获取数据库数据。
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
        List<Admin> result= adminMapper.selectByExample(adminExample);
        if(result.isEmpty())
            return null;
        return result.get(0);

    }

}
