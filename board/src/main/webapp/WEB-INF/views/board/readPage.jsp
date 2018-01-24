<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<style type="text/css">
.popup {
	position: absolute;
}
.back {
	background-color: gray;
	opacity: 0.5;
	width: 100%;
	height: 300%;
	overflow: hidden;
	z-index: 1101;
}
.front {
	z-index: 1110;
	opacity: 1;
	boarder: 1px;
	margin: auto;
}
.show {
	position: relative;
	max-width: 1200px;
	max-height: 800px;
	overflow: auto;
}
</style>
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js" type="text/javascript"></script> <!-- 템플릿 사용해서 첨부파일 밑에 보이는 템플릿 사용 -->
<script type="text/javascript" src="/resources/dist/js/upload.js"></script>

<script type="text/javascript">

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
	//게시물 삭제시에 첨부파일 삭제처리에 대한 Ajax삭제 
	$("#removeBtn").on("click",function(){
		// 정규식을사용해서 댓글의 갯수를 모두 가지고와서  하나라도 있으면 삭제처리를 못하게함 
		var replyCnt=$("#replycntStrong").html().replace(/[^0-9]/g,"");
		
		if(replyCnt>0){
			alert("댓글이 있는 게시물은 삭제 할수가 없습니다.");
			
			return;
		}
		var arr=[];
		$(".uploadedList li").each(function(index){
			//data-src의 값에 this에 해당하는 값을 push한다는 소리 
			arr.push($(this).attr("data-src"));
		});
		if(arr.length>0){
			$.post("/deleteAllFiles",{files:arr}, function(){
				
			});
		}
		formObj.attr("action","/board/removePage");
		formObj.submit();
	});
});
</script>

<!-- 날짜 출력 함수 부분  -->
<script type="text/javascript">
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
		//댓글이 보여지는것은 board에서 가지고 오지만 댓글을 삭제 했을때는 ajax를 통한 처리를 했기때문에 totalCount에 대한 정보를 가지고 와야한다
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
//썸네일 사진을 클릭했을시에 원본 사진을 크게 보게 하는 부분
$(".uploadedList").on("click",".mailbox-attachment-info a",function(event){
	
	var fileLink=$(this).attr("href");
	
	if(checkImageType(fileLink)){ //이미지파일일 경우 원본 파일 추가 
		
		event.preventDefault();
		
		var imgTag=$("#popup_img");
		imgTag.attr("src",fileLink);
		
		console.log(imgTag.attr("src"));
		
		$(".popup").show('slow');
		imgTag.addClass("show");
	}
});
//크게된 원본파일을 클릭시에 숨겨지는 효과 
$("#popup_img").on("click",function(){
	$(".popup").hide('slow');
});
//댓글 수정할수 없게 버튼 hidden (Handlebar를 사용해서 했기때문에 )
Handlebars.registerHelper("eqReplyer",function(replyer,block){
	
	var accum='';
	if(replyer=="${login.uid}"){ //session에 있는 아이디와 댓글단 replyer와 일치하면 block이 생기게 함 
		
		accum +=block.fn();
	}
	return accum;
});
</script>
<!-- 이미지 클릭시에 큰화면으로 보여주게 하는 부분  -->
<div class="popup back" style="display: none;"></div>
	<div id="popup_front" class="popup front" style="display: none;">
	<img id="popup_img">
	</div>
	
<form role="form" method="post" action=""> <!-- name이 파라미터로 넘어가는 거고 리턴으로 criteria 객체로 리턴받은값이  value -->
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
		<textarea class="form-control" name="content" rows="3" readonly="readonly" cols="">${boardVO.content}</textarea>
	</div>
	<div class="form-group">
		<label for="exampleInputEmail1">Writer</label>
		<input type="text" class="form-control" value="${boardVO.writer}" readonly="readonly">
	</div>
</div>
<!-- 파일 업로드정보가 보여지는 부분  -->
<ul class="mailbox-attachments clearfix uploadedList"></ul>

<div class="box-footer">
	<!-- 로그인한 사용자일 경우에만 수정 삭제만  -->
	<c:if test="${login.uid==boardVO.writer }">
		<button type="submit" class="btn btn-warning">Modify</button>
		<button type="submit" class="btn btn-danger" id="removeBtn">Remove</button>
	</c:if>
		<button type="submit" class="btn btn-primary">Go List</button>
</div>


<!-- 댓글 부분  -->
<div class="row">
	<div class="col-md-12">
		<div class="box box-success">
			<div class="box-header">
				<h3 class="box-title">댓글 등록</h3>
			</div>
			<!-- 세션에 정보가 없다면 댓글달수 없게 함  -->
			<c:if test="${not empty login}">
			<div class="box-body">
				<label for="newReplyWriter">글쓴이</label>
					<input class="form-control" type="text" placeholder="user_id" id="newReplyWriter">
				<label for="newReplyText"> 댓글내용</label>
					<textarea class="form-control" placeholder="reply_text" rows="3" id="newReplyText" cols=""></textarea>
			</div>
			<div class="box-footer">
				<button type="submit" class="btn btn-info" id="replyAddBtn">댓글등록하기</button>
			</div>
			</c:if>
			<!-- 댓글을 보고 싶으면 로그인 화면으로 매핑 할수 있게 처리  -->
			<c:if test="${empty login}">
				<div class="box-body">
					<div><a href="/user/login">Login Please</a></div>
				</div>
			</c:if>
		</div>
		 <!--댓글 페이징 처리(위에 handlebar 를 이용해서  li태그 목록을 가지고 오는 부분 --> 
		<ul	class="timeline">
			<li class="time-label" id="repliesDiv"><span class="bg-green">댓글 목록
			<!-- 댓글 갯수 가지고 오는 부분 -->
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
	<div class= "modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body" data-rno>
			<p><textarea id="replytext" class="form-control" placeholder="reply_text" cols="" rows=""></textarea></p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-info" id="replyModBtn">수정</button>
				<button type="button" class="btn btn-red" style="background-color: pink;"id="replyDelBtn">삭제</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>
<!-- 파일 업로드 처리도 handlebars 사용해서 처리  -->
<script id="templateAttach" type="text/x-handlebars-template">
<li data-src='{{fullName}}'>
	<span class="mailbox-attachment-icon has-img">
		<img src="{{imgsrc}}" alt="Attachment">
	</span>
	<div class="mailbox-attachment-info">
		<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
	</div>
</li>
</script>
<!--  handlebars 사용해서 li태그 반복 , 댓글 처리 할때 템플릿을 사용해서 li태그와 안에 데이터를 반복해서 처리--> 
<!--session에 있는 사용자 정보와 댓글을 단사람의 아이디와 일치 하지 않으면 수정할수 없게 설정{{eqReplyer}}--> 
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
		{{#eqReplyer replyer}}
     	<a class="btn btn-primary btn-xs" 
	    	data-toggle="modal" data-target="#modifyModal">Modify</a>
		{{/eqReplyer}}
    </div>
  </div>			
</li>
{{/each}}
</script>

<script type="text/javascript">
var bno=${boardVO.bno};
//만들어놓은 핸드바 템플릿을 가지고온후에  핸드바템플릿에 맞게 컴파일한후에 list 형태로 가지고온 데이터를 바인딩후 DOM객체로 만들어줌 
var template=Handlebars.compile($("#templateAttach").html());

$.getJSON("/sboard/getAttach/"+bno,function(list){
	
	$(list).each(function(){
		
		var fileInfo=getFileInfo(this);
		
		var html=template(fileInfo);
		
		$(".uploadedList").append(html);
	});
});
</script>
<%@ include file="../include/footer.jsp" %>
