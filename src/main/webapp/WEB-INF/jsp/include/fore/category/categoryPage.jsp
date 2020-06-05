<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<title>模仿天猫官网-${c.name}</title>
<div id="category">
    <div class="categoryPageDiv">
        <%--显示当前分类图片--%>
        <img src="img/category/${c.id}.jpg">
        <%--排序bar--%>
        <%@include file="sortBar.jsp" %>
        <%--分类下的产品--%>
        <%@include file="productsByCategory.jsp" %>
    </div>

</div>