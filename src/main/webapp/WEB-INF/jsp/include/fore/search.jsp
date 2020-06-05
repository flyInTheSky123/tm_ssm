<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" isELIgnored="false"%>

<a href="${contextPath}">
	<img id="logo" src="img/site/logo.gif" class="logo">
</a>

<form action="foresearch" method="post" >
	<div class="searchDiv">
		<input name="keyword" type="text" value="${param.keyword}" placeholder="时尚男鞋  太阳镜 ">
		<button  type="submit" class="searchButton">搜索</button>
		<div class="searchBelow">
			<%--varStatus:有 count(从一开始排序) ，current（显示地址），index（从0开始），first/last(判断是否是第一个/最后一个)--%>
			<c:forEach items="${cs}" var="c" varStatus="st">
				<c:if test="${st.count>=5 and st.count<=8}">
						<span>
							<a href="forecategory?cid=${c.id}">
									${c.name}
							</a>
							<c:if test="${st.count!=8}">
								<span>|</span>
							</c:if>
						</span>
				</c:if>
			</c:forEach>
		</div>
	</div>
</form>
