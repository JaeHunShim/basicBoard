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
		self.location="/board/listPage?page=${cri.page}&perPageNum=${cri.perPageNum}"; //uri로 정보를 보냄 그래야 Criteria 객체 안에 set해놓고 나중에 get해서 가져오지
		formObj.submit();
	});
	$(".btn-danger").on("click",function(){
		//self.loction="/board/listPage"; 검색기능 있기전
		self.location="/sboard/list";
	});
	
});

</script>

<form role="form" method="post">
	<input type="hidden" name="page" value="${cri.page }">
	<input type="hidden" name="perPageNum" value="${cri.perPageNum }">
	<div class="box-body">
		<div class="form-group">
			<label for="exampleInputEmail1">BNO</label>
			<input type="text" class="form-control" name= "bno" value="${boardVO.bno}" readonly="readonly">
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