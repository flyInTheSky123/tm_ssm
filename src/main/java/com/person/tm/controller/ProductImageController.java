package com.person.tm.controller;

import com.person.tm.pojo.Product;
import com.person.tm.pojo.ProductImage;
import com.person.tm.service.ProductImageService;
import com.person.tm.service.ProductService;
import com.person.tm.util.ImageUtil;
import com.person.tm.util.Page;
import com.person.tm.util.UploadImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductImageController {

    @Autowired
    ProductImageService productImageService;

    @Autowired
    ProductService productService;

    //查询
    @RequestMapping("admin_productImage_list")
    public String list(int pid, Model model) {
        //通过产品id ，获取产品信息。用于面包屑导航
        Product product = productService.get(pid);

        //获取pid 中type_detail和type_single类型的数据
        List<ProductImage> list_detail = productImageService.list(pid, productImageService.type_detail);
        List<ProductImage> list_single = productImageService.list(pid, productImageService.type_single);

        model.addAttribute("p", product);
        model.addAttribute("pisDetail", list_detail);
        model.addAttribute("pisSingle", list_single);

        return "admin/listProductImage";
    }

    //删除
    @RequestMapping("admin_productImage_delete")
    public String delete(int id) {
        ProductImage productImage = productImageService.get(id);
        productImageService.delete(id);
        //通过productImage 得到产品的ID
        return "redirect:admin_productImage_list?pid=" + productImage.getPid();
    }

    @RequestMapping("admin_productImage_add")
    public String add(ProductImage pi, HttpSession session, UploadImageFile uploadImageFile) throws IOException {
        //添加产品图片
        productImageService.add(pi);
        //通过pi,获得产品信息，用于查询
        Product product = productService.get(pi.getPid());


        //创建图片文件（分为两种 1，type_detail（详情图片），   2,type_single(单独图片。正常大小，small（小），middle（中)。）
        //创建正常类型singl， small(小)，middle（中）；
        String fileFolder = null;
        String singleFolder_Small = null;
        String singleFolder_Middle = null;

        //图片文件名称
        String picName = pi.getId() + ".jpg";

        //判断添加 的图片类型
        if (productImageService.type_single.equals(pi.getType())) {
            //图片类型是single
            fileFolder = session.getServletContext().getRealPath("img/productSingle");
            singleFolder_Small = session.getServletContext().getRealPath("img/productSingle_middle");
            singleFolder_Middle = session.getServletContext().getRealPath("img/productSingle_small");

        }else {
            //图片类型是detail
            fileFolder = session.getServletContext().getRealPath("img/productDetail");
        }
        File file = new File(fileFolder,picName);
        //判断该文件是否存在
        if (!file.exists()){
            file.getParentFile().mkdirs();
        }
        //将获取的图片传入file 中
        uploadImageFile.getImage().transferTo(file);
        //将图片转换为jpg格式
        BufferedImage bufferedImage = ImageUtil.change2jpg(file);
        //将bufferedImage，以jpg格式，写入file中
        ImageIO.write(bufferedImage,"jpg",file);


        //判断图片类型，如果是single ，则将 传入的图片文件file ,改为中型（217，190），小型（56，56）
        if (productImageService.type_single.equals(pi.getType())){
            File file_small = new File(singleFolder_Small, picName);
            File file_middle = new File(singleFolder_Middle, picName);

            ImageUtil.resizeImage(file,217,190,file_middle);
            ImageUtil.resizeImage(file,56,56,file_small);

        }

        return "redirect:admin_productImage_list?pid=" + product.getId();
    }

}
