package com.person.tm.comparator;

import com.person.tm.pojo.Product;

import java.util.Comparator;


//评论数量比较（人气比较）
public class ProductReviewComparator implements Comparator<Product> {


    @Override
    public int compare(Product p1, Product p2) {
        return  p2.getReviewCount()-p1.getReviewCount();
    }
}
