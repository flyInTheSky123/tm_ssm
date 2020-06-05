package com.person.tm.service;

import com.person.tm.pojo.Review;

import java.util.List;

//评论
public interface ReviewService {
    void add(Review c);
    void delete(int id);
    void update(Review c);
    //通过ID获取单个评论
    Review get(int id);
    //通过产品id获取该产品下的所有的评论
    List list(int pid);
    //通过产品id 获取该产品的评论条数。
    int getCount(int pid);
}
