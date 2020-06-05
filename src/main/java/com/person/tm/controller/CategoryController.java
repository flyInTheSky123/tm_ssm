package com.person.tm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.person.tm.pojo.Category;
import com.person.tm.service.CategoryService;
import com.person.tm.service.PropertyService;
import com.person.tm.util.ImageUtil;
import com.person.tm.util.Page;
import com.person.tm.util.UploadImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    PropertyService propertyService;

    @RequestMapping("admin_category_list")
    public String list(Model model, Page page) {
        //通过使用PageHelper 插件，提前设置 开始位置，每页条数。
        PageHelper.offsetPage(page.getStart(),page.getCount());
        System.out.println("count:------"+page.getCount());
        //通过page 参数得到相应的数据库数据
        List<Category> cs = categoryService.list();

        //获取总的数据条数
        int total = (int) new PageInfo<>(cs).getTotal();
        //赋值
        page.setTotal(total);
        model.addAttribute("page", page);
        model.addAttribute("cs", cs);
        //跳转到listCategory.jsp 下并且将参数model 传递过去
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")
    public String add(Category category, HttpSession session, UploadImageFile uploadImageFile) throws IOException {
        //插入新的分类
        categoryService.add(category);

        //创建文件夹和文件
        File fileFolder = new File(session.getServletContext().getRealPath("img/category"));

        // 这里的category.getId()为什么可以自动获取新建id？
        // 因为：因为在Mapper.xml中的<insert id="add" keyProperty="id" useGeneratedKeys="true"></insert>
        File file = new File(fileFolder, category.getId() + ".jpg");

        //判断当前文件是否存在
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        //将图片放进file文件中
        uploadImageFile.getImage().transferTo(file);
        //将图片转化为jpg格式。
        BufferedImage bufferedImage = ImageUtil.change2jpg(file);

        //将图片bufferImage 以 jpg格式写进 文件file中。
        ImageIO.write(bufferedImage, "jpg", file);

        return "redirect:/admin_category_list";

    }

    @RequestMapping("admin_category_delete")
    public String delete(Category category, HttpSession session) {
        //先删除该分类分类下的property属性
        propertyService.deletByCid(category.getId());
        //删除数据库中的数据
        categoryService.delete(category);
        //删除文件里面的图片
        File fileFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(fileFolder, category.getId() + ".jpg");
        fileFolder.delete();

        return "redirect:/admin_category_list";
    }


    @RequestMapping("admin_category_edit")
    public String get(int id, Model model) {
        Category cs = categoryService.get(id);
        model.addAttribute("c", cs);
        return "admin/editCategory";
    }

    @RequestMapping("admin_category_update")
    public String update(Category category, HttpSession session, UploadImageFile uploadImageFile) throws IOException {
        //将要修改的分类名称 进行更新
        categoryService.update(category);

        MultipartFile image = uploadImageFile.getImage();
        //判断是否修改了图片
        if (null != image && !image.isEmpty()) {
            File fileFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(fileFolder,category.getId()+".jpg");
            image.transferTo(file);
            BufferedImage bufferedImage = ImageUtil.change2jpg(file);
            ImageIO.write(bufferedImage,"jpg",file);
        }
        return "redirect:/admin_category_list";

    }
}