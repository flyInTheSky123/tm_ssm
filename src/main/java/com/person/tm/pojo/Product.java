package com.person.tm.pojo;

import java.util.Date;
import java.util.List;


public class Product {
    private Integer id;

    private String name;

    private String subTitle;   //标题

    private Float originalPrice;//原价

    private Float promotePrice; //折扣价

    private Integer stock;      //库存数量

    private Integer cid;        //该产品的分类id

    private Date createDate;    //创建时间

    //非数据库字段
    private Category category;  //该产品的分类模型

    private ProductImage productFirstImage; //设置产品管理的主图片

//----------------前端页面单个产品所需要的数据--------
    //单个产品图片集合
    private List<ProductImage> productSingleImages;

    //详情图片集合
    private List<ProductImage> productDetailImages;

    //销量
    private  int saleCount;

    //评价数量
    private int reviewCount;

    public List<ProductImage> getProductSingleImages() {
        return productSingleImages;
    }

    public void setProductSingleImages(List<ProductImage> productSingleImages) {
        this.productSingleImages = productSingleImages;
    }

    public List<ProductImage> getProductDetailImages() {
        return productDetailImages;
    }

    public void setProductDetailImages(List<ProductImage> productDetailImages) {
        this.productDetailImages = productDetailImages;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public ProductImage getProductFirstImage() {
        return productFirstImage;
    }

    public void setProductFirstImage(ProductImage productFirstImage) {
        this.productFirstImage = productFirstImage;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    public Float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(Float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}