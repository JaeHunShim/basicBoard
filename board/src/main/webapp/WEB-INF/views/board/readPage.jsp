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
	//댓글 등록처리 
	$("#replyAddBtn").on("click",function(){
		var replyerObj = $("#newReplyWriter");
		var replytextObj = $("#newReplyText");
		
		var replyer = replyerObj.val();
		var replytext= replytextObj.val();
		
		$.ajax({
			type:"post",
			url:"/replies/",
			headers:{
				"Content-Type":"application/json",
				"X-Http-Method-Override":"POST"
			},
			dataType:"text",
			data:JSON.stringify({bno:bno, replyer:replyer,replytext:replytext}),
			success:function(result){
				console.log("result:"+result);
				if(result=="SUCCESS"){
					alert("등록에 성공했습니다.");
					relyPage=1; //페이지 번호를 다시 1로 초기화
					getPage("/replies/"+bno+"/"+replyPage); // 조건에 맞는 (bno, replyPage)페이지 정보
					//다시 초기화 
					replyerObj.val("");
					replytextObj.val("");
				}
			}	
		});
	});
		//페이징 처리 글 번호에 맞는 댓글 가지고 오는 부분 
	$("#repliesDiv").on("click", function() {

		if ($(".timeline li").size() > 1) {
			return;
		}
		getPage("/replies/" + bno + "/1");

	});

		//페이징 처리(하단부)
	$(".pagination").on("click", "li a", function(event){
		
		event.preventDefault();
		
		replyPage = $(this).attr("href");	
		
		getPage("/replies/"+bno+"/"+replyPage);
		
	});
		//댓글 수정에 페이지 modal 여는 부분 
	$(".timeline").on("click",".replyLi", function(){
		var reply =$(this); //해당 replyLi
		
		$("#replytext").val(reply.find(".timeline-body").text());
		$(".modal-title").html(reply.attr("data-rno")); //<h4>태그의 modal-title에 rno 값을 추가 
		
	});
		//수정버튼 클릭 이벤트
	$("#replyModBtn").on("click",function(){
		//각각의 해당 데이터를 뽑아옴
		var rno= $(".modal-title").html();
		var replytext=$("#replytext").val();
		
		$.ajax({
			type:"put",
			url:"/replies/"+rno,
			headers:{
				"Content-Type":"application/json",
				"X-Http-Method-Override":"PUT"
			},
			dataType:"text",
			data:JSON.stringify({rno:rno,replytext:replytext}),
			success:function(result){
				if(result=="SUCCESS"){
					console.log("result"+result);
					alert("수정되었습니다.");
					getPage("/replies/"+bno+"/"+replyPage);
				}
			}
		});
	});	
	//댓글 삭제
	$("#replyDelBtn").on("click",function(){
		var rno=$(".modal-title").html();
		var replytext=$("#replytext").val();
		
		$.ajax({
			type:"delete",
			url:"/replies/"+rno,
			headers:{
				"Content-Type":"application/json",
				"X-Http-Method-Override":"DELETE"
			},
			dataType:"text",
			success:function(result){
				console.log("result:"+result);
				if(result=="SUCCESS"){
					alert("삭제되었습니다.");
					getPage("/replies/"+bno+"/"+replyPage);
				}
			}
		});
	});
	
	
});
//handlebars 사용해서 li태그 반복
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
		//댓글이 보여지는것은 board에서 가지고 오지만 댓글을 삭제 했을때는 ajax를 통한 처리를 했기때문에 totalCount에 대한 정보를 가지고 와야한다. 
		$("#replycntStrong").html("["+data.pageMaker.totalCount+"]");

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
				<button type="submit" class="btn btn-info" id="replyAddBtn">댓글등록하기</button>
			</div>
		</div>
		 <!--댓글 페이징 처리(위에 handlebar 를 이용해서  li태그 목록을 가지고 오는 부분 --> 
		<ul	class="timeline">
			<li class="time-label" id="repliesDiv"><span class="bg-green">댓글 목록
			<strong id="replycntStrong">[${boardVO.replycnt}]</strong></span></li>
		</ul>
		<!-- 댓글 하단부분(버튼)-->
		<div class="text-center">
			<ul id="pagination" class="pagination pagination-sm no-margin">
			</ul>
		</div>
	</div>
</div>
<!-- 댓글 수정 처리 부분 (modal) -->
<div id="modifyModal" class="modal modal-primary fade" role="dialog">
	<div class= modal-dialog>
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body" data-rno>
			<p><textarea id="replytext" class="form-control" placeholder="reply_text"></textarea></p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info" id="replyModBtn">수정</button>
				<button type="button" class="btn btn-red" style="background-color:pink;"id="replyDelBtn">삭제</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>
	
<%@ include file="../include/footer.jsp" %>