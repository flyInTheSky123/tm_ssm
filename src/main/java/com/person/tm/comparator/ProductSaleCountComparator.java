package com.person.tm.comparator;

import com.person.tm.pojo.Product;

import java.util.Comparator;


//产品的销量比较（销量比较），把销量高的放在前面
public class ProductSaleCountComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getSaleCount()-p1.getSaleCount();
    }
}
