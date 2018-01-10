<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>

<script>
$(document).ready(function(){

	var formObj=$("form[role='form']");
	
	console.log(formObj);
	
	$(".btn-warning").on("click",function(){ //attr: 추가
		formObj.attr("action","/board/modifyPage");
		formObj.attr("method","get");
		formObj.submit();
	});
	 $(".btn-danger").on('click',function(){
		formObj.attr("action","/board/removePage");
		formObj.submit();
	 });
	$(".btn-primary").on("click",function(){
		formObj.attr("action","/board/listPage");
		formObj.attr("method","get");
		formObj.submit();
	});
});

</script>

<form role="form" method="post"> <!-- name이 파라미터로 넘어가는 거고 리턴으로 criteria 객체로 리턴받은값이  value -->
	<input type="hidden" name="bno" value="${boardVO.bno}">
	<input type="hidden" name="page" value="${cri.page}">
	<input type="hidden" name="perPageNum" value="${cri.perPageNum}">
</form>
<div class="box-body">
	<div class="form-group">
		<label for="exampleInputEmail1">Title</label>
		<input type="text" class="form-control" value="${boardVO.title}" readonly="readonly">
	</div>
	<div class="form-group">
		<label for="examplePassword1">Content</label>
		<textarea class="form-control" name="content" rows="3" readonly="readonly">${boardVO.content}</textarea>
	</div>
	<div class="form-group">
		<label for="exampleInputEmail1">Writer</label>
		<input type="text" class="form-control" value="${boardVO.writer}" readonly="readonly">
	</div>
</div>
<div class="box-footer">
	<button type="submit" class="btn btn-warning">Modify</button>
	<button type="submit" class="btn btn-danger">Remove</button>
	<button type="submit" class="btn btn-primary">ListAll</button>
</div>

<%@ include file="../include/footer.jsp" %>