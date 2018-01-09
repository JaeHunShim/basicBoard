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
		formObj.attr("action","/board/modify");
		formObj.attr("method","get");
		formObj.submit();
	});
	 $(".btn-danger").on('click',function(){
		formObj.attr("action","/board/remove");
		formObj.submit();
	 });
	$(".btn-primary").on("click",function(){
		self.location="/board/listAll";
	});
});

</script>

<form role="form" method="post">
	<input type="hidden" name="bno" value="${boardVO.bno}">
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