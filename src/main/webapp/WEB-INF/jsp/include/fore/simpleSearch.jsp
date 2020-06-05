<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 2020-02-19
  Time: 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<div>
    <a href="${contextPath}">
        <img id="simpleLogo" class="simpleLogo" src="img/site/simpleLogo.png">
    </a>

    <form method="post" action="foresearch">
        <div class="simpleSearchDiv pull-right">
            <input type="text" value="${param.keyword}" placeholder="冰箱 家具" name="keyword">
            <button class="searchButton" type="submit">搜索</button>
            <div class="searchBelow">
                <c:forEach items="cs" var="c" varStatus="st">
                    <c:if test="${st.count>=8 and st.count<=11}">
                        <span>
                            <a href="forecategory?cid=${c.id}">
                                    ${c.name}
                            </a>
                            <c:if test="${st.count!=11}">
                                <span>|</span>
                            </c:if>

                        </span>
                    </c:if>

                </c:forEach>

            </div>
        </div>
    </form>
    <div class="clear:both"></div>
</div>