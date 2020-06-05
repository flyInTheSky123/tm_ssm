package com.person.tm.comparator;

import com.person.tm.pojo.Product;

import java.util.Comparator;


//产品的售价比较（价格比较），把价格低的放在前面
public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        //如果p1>p2 返回 正数 ，p1=p2 返回 0 ，否则返回负数
        return Float.compare(p1.getPromotePrice(),p2.getPromotePrice());
    }
}
