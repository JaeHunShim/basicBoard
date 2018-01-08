<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<!-- <meta http-equiv="Content-Type" content="text/html;charset=utf-8" /> -->
<h3>게시판 목록</h3>
<table class="table table-bordered">
	<tr  style="border:5px;border-color:red">
		<th>번호</th>
		<th>제목</th>
		<th>글쓴이</th>
		<th>작성날짜</th>
		<th>조회수</th>
	</tr>
</table>
<%@ include file="../include/footer.jsp" %>