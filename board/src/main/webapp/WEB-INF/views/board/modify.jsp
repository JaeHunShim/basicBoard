<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>

<script>
$(document).ready(function(){

	var formObj=$("form[role='form']");
	console.log(formObj);
	
	$(".btn-warning").on("click",function(){
		formObj.submit();
	});
	$(".btn-danger").on("click",function(){
		self.loction="/board/listAll";
	});
	
});

</script>

<form role="form" method="post">
	<div class="box-body">
		<div class="form-group">
			<label for="exampleInputEmail1">BNO</label>
			<input type="text" class="form-control" value="${boardVO.bno}" readonly="readonly">
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">Title</label>
			<input type="text" class="form-control" value="${boardVO.title}" readonly="readonly">
		</div>
		<div class="form-group">
			<label for="examplePassword1">Content</label>
			<textarea class="form-control" name="content" rows="3">${boardVO.content}</textarea>
		</div>
		<div class="form-group">
			<label for="exampleInputEmail1">Writer</label>
			<input type="text" class="form-control" value="${boardVO.writer}" readonly="readonly">
		</div>
	</div>
</form>
<div class="box-footer">
	<button type="submit" class="btn btn-warning">Save</button>
	<button type="submit" class="btn btn-danger">Cancel</button>
</div>

<%@ include file="../include/footer.jsp" %>