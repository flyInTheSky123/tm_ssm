package com.person.tm.comparator;

import com.person.tm.pojo.Product;

import java.util.Comparator;


//综合比较，把数字大的放在前面
public class ProductAllComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        //通过该产品的评论总量 * 该产品的出售数量 作为综合比较的依据
        return p2.getReviewCount()*p2.getSaleCount()-p1.getSaleCount()*p1.getReviewCount();
    }
}
