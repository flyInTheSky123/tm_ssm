package com.person.tm.util;

import org.springframework.web.multipart.MultipartFile;
//上传图片：
public class UploadImageFile {
// 这里的属性名称image必须和页面中的增加分类部分中的type="file"的name值保持一致。
//<input id="categoryPic" accept="image/*" type="file" name="image" />
    MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
