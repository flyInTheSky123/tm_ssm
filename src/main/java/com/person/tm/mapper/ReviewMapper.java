package com.person.tm.mapper;

import com.person.tm.pojo.Review;
import com.person.tm.pojo.ReviewExample;
import java.util.List;
//评论
public interface ReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Review record);

    int insertSelective(Review record);

    List<Review> selectByExample(ReviewExample example);

    Review selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Review record);

    int updateByPrimaryKey(Review record);
}