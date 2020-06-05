package com.person.tm.service.impl;

import com.person.tm.mapper.ReviewMapper;
import com.person.tm.pojo.Review;
import com.person.tm.pojo.ReviewExample;
import com.person.tm.pojo.User;
import com.person.tm.service.ReviewService;
import com.person.tm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//评论
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    UserService userService;
    @Override
    public void add(Review c) {
        reviewMapper.insertSelective(c);

    }

    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review c) {
        reviewMapper.updateByPrimaryKeySelective(c);

    }

    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    //通过产品id 获取该产品下的所有的评论。
    @Override
    public List list(int pid) {
        ReviewExample reviewExample = new ReviewExample();
        reviewExample.createCriteria().andPidEqualTo(pid);
        reviewExample.setOrderByClause("id desc");
        List<Review> reviews = reviewMapper.selectByExample(reviewExample);
        setUser(reviews);
        return reviews;
    }


    public void  setUser(List<Review> reviews){
        for (Review r:reviews){
            setUser(r);
        }

    }

    public void  setUser(Review review){
        //获取评论中的uid 字段
        Integer uid = review.getUid();
        //通过uid 获取user
        User user = userService.get(uid);
        //设置写该评论的用户。
        review.setUser(user);
    }

    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }
}
