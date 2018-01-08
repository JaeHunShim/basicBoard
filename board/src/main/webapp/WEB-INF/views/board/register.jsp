<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<!-- <meta http-equiv="Content-Type" content="text/html;charset=utf-8" /> -->
<form role="form" method="post" style="padding:10px;">
<div class="box-body"></div>
	<div class="form-group">
		<label for="exmpleInputEmail">Title</label>
		<input type="text" name="title" class="form-control" placeholder="Enter Title">
	</div>
	<div class="form-group">
		<label for="exmpleInputPassword1">Content</label>
		<textarea class="form-control" name="content" rows="3" placeholder="Enter....."></textarea>
	</div>
	<div class="form-group">
		<label for="exmpleInputEmail">Writer</label>
		<input type="text" name="writer" class="form-control" placeholder="Enter Writer">
	</div>
	<!-- end body -->
	<div class="box-footer">
		<button type="submit" class="btn btn-primary">Submit</button>
	</div>
</form>
<%@ include file="../include/footer.jsp" %>
