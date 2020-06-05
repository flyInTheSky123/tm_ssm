package com.person.tm.comparator;

import com.person.tm.pojo.Product;

import java.util.Comparator;


//产品上架时间的早晚比较（新品比较），把创建时间晚的放在前面
public class ProductDateComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {

        //通过该产品的上架时间 作为综合比较的依据.
        /**
         * x.compareTo(y)：
         *  1，如果比较的是时间日期类型，当x>y ，返回 1，   x=y 返回 0   否则 -1。
         *  2，如果是日期String，如String time="2020-01-10 12:34:00" 。
         *      当x>y 返回正整数 ，x=y 返回0 ，否则返回负整数。
         *  3，compare（）
         */
        return p2.getCreateDate().compareTo(p1.getCreateDate());
    }


}
