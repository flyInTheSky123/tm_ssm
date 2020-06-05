package com.person.tm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.person.tm.pojo.Category;
import com.person.tm.pojo.Property;
import com.person.tm.pojo.PropertyValue;
import com.person.tm.service.CategoryService;
import com.person.tm.service.PropertyService;
import com.person.tm.service.PropertyValueService;
import com.person.tm.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//属性控制类
@Controller
@RequestMapping
public class PropertyController {
    @Autowired
    PropertyService propertyService;
    @Autowired
    CategoryService categoryService;


    @RequestMapping("admin_property_list")
    public String list(int cid, Page page, Model model){
        //通过属性中的cid 查询分类中的数据
        Category c = categoryService.get(cid);

        //设置分页插件的 参数（开始位置，每页条数）
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Property> list = propertyService.list(cid);
        int total = (int) new PageInfo<>(list).getTotal();

        //设置分页总条数
        page.setTotal(total);
        //分页设置参数
        page.setParam("&cid="+c.getId());

        model.addAttribute("c",c);
        model.addAttribute("page",page);
        model.addAttribute("ps",list);
       return  "admin/listProperty";
    }


    @RequestMapping("admin_property_edit")
    public String edit(int id,Model model){
        Property property = propertyService.get(id);
        Category category = categoryService.get(property.getCid());
        property.setCategory(category);
        model.addAttribute("p",property);

        return "admin/editProperty";

    }

    @RequestMapping("admin_property_update")
    public String edit(Property property){
       propertyService.update(property);
      return   "redirect:admin_property_list?cid="+property.getCid();

    }

    @RequestMapping("admin_property_add")
    public String add(Property property,Model model){
       propertyService.add(property);

        return "redirect:admin_property_list?cid="+property.getCid();

    }
    @RequestMapping("admin_property_delete")
    public String delete(int id,Model model){

        //获取该id 的信息
        Property property = propertyService.get(id);
        //删除该 属性
        propertyService.delete(property.getId());

        return "redirect:admin_property_list?cid="+property.getCid();

    }

}
