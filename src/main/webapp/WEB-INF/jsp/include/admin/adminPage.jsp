<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<script>
$(function(){
	$("ul.pagination li.disabled a").click(function(){
		return false;
	});
});
//分页jsp使用bootstrap
</script>
<nav>
  <ul class="pagination">
      <%--当page.hasPreviouse为false的时候，为首页连接套用Bootstrap样式 disabled，以下同理。--%>
    <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
      <a  href="?start=0${page.param}" aria-label="Previous" >
        <span aria-hidden="true">首页</span>
      </a>
    </li>

    <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
      <a  href="?start=${page.start-page.count}${page.param}" aria-label="Previous" >
        <span aria-hidden="true">上一页</span>
      </a>
    </li>    

    <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
		    <li <c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>
		    	<a  
		    	href="?start=${status.index*page.count}${page.param}"
		    	<c:if test="${status.index*page.count==page.start}">class="current"</c:if>
		    	>${status.count}</a>
		    </li>
    </c:forEach>

    <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
      <a href="?start=${page.start+page.count}${page.param}" aria-label="Next">
        <span aria-hidden="true">下一页</span>
      </a>
    </li>
    <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
      <a href="?start=${page.last}${page.param}" aria-label="Next">
        <span aria-hidden="true">页尾</span>
      </a>
    </li>
  </ul>
</nav>
