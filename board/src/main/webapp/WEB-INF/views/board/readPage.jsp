<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

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
		//formObj.attr("action","/board/listPage"); 검색기능 있기전
		formObj.attr("action","/sboard/list");
		formObj.attr("method","get");
		formObj.submit();
	});
	
	$("#repliesDiv").on("click", function() {

		if ($(".timeline li").size() > 1) {
			return;
		}
		getPage("/replies/" + bno + "/1");

	});


	$(".pagination").on("click", "li a", function(event){
		
		event.preventDefault();
		
		replyPage = $(this).attr("href");
		
		getPage("/replies/"+bno+"/"+replyPage);
		
	});
});
</script>
<script id="template" type="text/x-handlebars-template">
{{#each .}}
<li class="replyLi" data-rno={{rno}}>
<i class="fa fa-comments bg-blue"></i>
 <div class="timeline-item" >
  <span class="time">
    <i class="fa fa-clock-o"></i>{{prettifyDate regdate}}</span>
  <h3 class="timeline-header"><strong>{{rno}}</strong> -{{replyer}}</h3>
  <div class="timeline-body">{{replytext}} </div>
    <div class="timeline-footer">
     <a class="btn btn-primary btn-xs" 
	    data-toggle="modal" data-target="#modifyModal">Modify</a>
    </div>
  </div>			
</li>
{{/each}}
</script>

<!-- 날짜 출력 함수 부분  -->
<script>
Handlebars.registerHelper("prettifyDate", function(timeValue) {
	var dateObj = new Date(timeValue);
	var year = dateObj.getFullYear();
	var month = dateObj.getMonth() + 1;
	var date = dateObj.getDate();
	return year + "/" + month + "/" + date;
});

var printData = function(replyArr, target, templateObject) {
	var template = Handlebars.compile(templateObject.html());
	var html = template(replyArr);
	$(".replyLi").remove();
	target.after(html);

}

var bno = ${boardVO.bno};
var replyPage = 1;
//페이징 처리 (게시물 목록 #modifyModal 부분을 클릭하면 댓글이 보이게 처리 )
function getPage(pageInfo) {
	$.getJSON(pageInfo, function(data) {
		printData(data.list, $("#repliesDiv"), $('#template'));
		printPaging(data.pageMaker, $(".pagination"));

		$("#modifyModal").modal('hide');

	});
}
// 페이징처리 (하단 버튼 부분처리)
var printPaging = function(pageMaker, target) {

	var str = "";
	if (pageMaker.prev) {
		str += "<li><a href='" + (pageMaker.startPage - 1)
				+ "'> << </a></li>";
	}
	for (var i = pageMaker.startPage, len = pageMaker.endPage; i <= len; i++) {
		var strClass = pageMaker.cri.page == i ? 'class=active' : '';
		str += "<li "+strClass+"><a href='"+i+"'>" + i + "</a></li>";
	}

	if (pageMaker.next) {
		str += "<li><a href='" + (pageMaker.endPage + 1)
				+ "'> >> </a></li>";
	}
	target.html(str);
}




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

<!-- 댓글 부분  -->
<div class="row">
	<div class="col-md-12">
		<div class="box box-success">
			<div class="box-header">
				<h3 class="box-title">댓글 등록</h3>
			</div>
			<div class="box-body">
				<label for="newReplyWriter">글쓴이</label>
					<input class="form-control" type="text" placeholder="user_id" id="newReplyWriter">
				<label for="newReplyText"> 댓글내용</label>
					<textarea class="form-control" placeholder="reply_text" rows="3" id="newReplyText"></textarea>
			</div>
			<div class="box-footer">
				<button type="submit" class="btn btn-primary" id="replyAddBtn">댓글등록하기</button>
			</div>
		</div>
		 <!--댓글 페이징 처리--> 
		<ul	class="timeline">
			<li class="time-label" id="repliesDiv"><span class="bg-green">댓글 목록</span></li>
		</ul>
		<!-- 댓글 하단부분(버튼)-->
		<div class="text-center">
			<ul id="pagination" class="pagination pagination-sm no-margin">
			</ul>
		</div>
	</div>
</div>
	
<%@ include file="../include/footer.jsp" %>