package com.person.tm.mapper;

import com.person.tm.pojo.User;
import com.person.tm.pojo.UserExample;
import java.util.List;
//用户
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}