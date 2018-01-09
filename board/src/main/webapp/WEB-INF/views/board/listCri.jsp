<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<!-- <meta http-equiv="Content-Type" content="text/html;charset=utf-8" /> -->
<script>
	var result='${msg}';
	
	if (result=='success'){
		
		alert('처리가 완료 되었습니다.');
	}
</script>

<h3>게시판 목록</h3>
<table class="table table-bordered">
	<tr style="border:5px;border-color:black">
		<th>번호</th>
		<th>제목</th>
		<th>글쓴이</th>
		<th>작성날짜</th>
		<th>조회수</th>
	</tr>
	<c:forEach items="${list}" var="boardVO">
		<tr>
			<td>${boardVO.bno}</td>
			<td><a href="/board/read?bno=${boardVO.bno}">${boardVO.title}</a></td>
			<td>${boardVO.writer}</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${boardVO.regdate}"/></td>
			<td><span class="badge bg-red">${boardVO.viewcnt}</span></td>
		</tr>
	</c:forEach>
</table>
<%@ include file="../include/footer.jsp" %>