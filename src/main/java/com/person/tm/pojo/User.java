package com.person.tm.pojo;

public class User {
    private Integer id;

    private String name;

    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    //匿名函数。当显示评价者的时候，进行匿名显示。
    public String getAnonymousName(){
        //将 用户名 根据大小进行分类。
        if (null==name){
            return null;
        }

        if (name.length()<=1){
            return "*";
        }

        if (name.length()==2){
            return name.substring(0,1)+"*";
        }

        //将字符串分为字节数组。
        char[] chars = name.toCharArray();
        for (int i=1;i<chars.length-1;i++){
            //将下标为： 1～length-1 的数组内容 使用'*' 填充。
            chars[i]='*';
        }

        return new String(chars);

    }
}