<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    <%--进入页面并且登录后，右上角动态显示购物车的数量--%>
  $(function () {
      var numberPage = "forecartNumber";
      var total;
      var page = "forecheckLogin";
      $.get(
          page,
          function (result) {
              if ("success" == result) {
                  $.get(
                      numberPage,
                      function (result) {
                          if (result != null) {
                              total = result;
                              $("#total").html(total);
                          } else {

                          }
                      }
                  )
              }
          }
      )

  })
</script>

<nav class="top ">
    <a href="${contextPath}">
        <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
        首页
    </a>

    <span>喵，欢迎来到商城</span>

    <c:if test="${!empty user}">
        <a href="loginPage">${user.name}</a>
        <a href="foreout">退出</a>
    </c:if>

    <c:if test="${empty user}">
        <a href="loginPage">请登录</a>
        <a href="registerPage">免费注册</a>
    </c:if>


    <span class="pull-right">
			<a href="forebought">我的订单</a>
			<a href="forecart">
			<span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
				购物车<strong id="total"> </strong>件</a>
		</span>


</nav>



