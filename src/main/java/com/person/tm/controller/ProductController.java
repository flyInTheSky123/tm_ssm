package com.person.tm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.person.tm.pojo.Category;
import com.person.tm.pojo.Product;
import com.person.tm.pojo.PropertyValue;
import com.person.tm.service.CategoryService;
import com.person.tm.service.ProductImageService;
import com.person.tm.service.ProductService;
import com.person.tm.service.PropertyValueService;
import com.person.tm.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;


    @Autowired
    PropertyValueService propertyValueService;

    @Autowired
    ProductImageService productImageService;

    //显示产品
    @RequestMapping("admin_product_list")
    public String list(Model model, int cid, Page page) {
        //通过传进来的 cid,得到相应的分类
        Category category = categoryService.get(cid);
        //为分页设置 开始位置，每页条数。等同与 limit start,count;
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Product> list = productService.list(category.getId());
        //获取数据的总条数
        int total = (int) new PageInfo<>(list).getTotal();

        page.setTotal(total);
        page.setParam("&cid=" + category.getId());
        model.addAttribute("page", page);
        model.addAttribute("ps", list);
        model.addAttribute("c", category);

        return "admin/listProduct";

    }

    //添加产品
    @RequestMapping("admin_product_add")
    public String add(Product product) {
        product.setCreateDate(new Date());
        productService.add(product);
        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    //查找指定产品

    @RequestMapping("admin_product_edit")
    public String find(int id, Model model) {
        //通过 传递进来的 产品id ，获取相应的 产品数据。
        Product product = productService.get(id);
        model.addAttribute("p", product);
        //将数据传进 目标中
        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String update(Product product) {
        //更新产品
        productService.update(product);

        return "redirect:admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_delete")
    public String delete(int id) {
        //通过ID 获取产品数据
        Product product = productService.get(id);

        List<PropertyValue> propertyValues = propertyValueService.listByPid(id);

        //删除该产品下的所有propertyvalue（属性值）
         propertyValueService.deleteByPid(id);

         //删除该产品下的所有产品图片
        productImageService.deleteByPid(id);

        //通过ID 删除该产品
        productService.delete(id);

        return "redirect:admin_product_list?cid=" + product.getCid();
    }
}
