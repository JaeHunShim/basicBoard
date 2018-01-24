<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>
<!-- <meta http-equiv="Content-Type" content="text/html;charset=utf-8" /> -->
<style>
.fileDrop{
	width:80%;
	height:100px;
	border:1px dotted green;
	background-color:green;
	margin:auto;
}
</style>
<form role="form" method="post" style="padding:10px;" id="registerForm">
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
		<!-- <input type="text" name="writer" class="form-control" placeholder="Enter Writer"> -->
		<!-- 세션에 저장되어있는 아이디를 받아와서 사용 -->
		<input type="text" name="writer" class="form-control" value="${login.uid}" readonly>
	</div>
		<div class="form-group">
		<label for="exampleInputEmail1">파일을 끌어다 놓아주세요!</label>
		<div class="fileDrop"></div>
	</div>
<div class="box-footer">
	<div>
		<hr>
	</div>
	<!--  ul 다음 li태그가 template로  생기게 되는 부분 -->
	<ul class="mailbox-attachments clearfix uploadedList">
	
</ul>
	<button type="submit" class="btn btn-primary">Submit</button>
</div>
</form>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script> <!-- 템플릿 사용해서 첨부파일 밑에 보이는 템플릿 사용 -->
<script type="text/javascript" src="/resources/dist/js/upload.js"></script>
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<!--파일 드랍하는 영역부분 설정 -->
<!--  template를 이용해서 반복문으로 계속 해서 영역을 만들어줌  -->
<script id="template" type="text/x-handlebars-template">
	<li>
		<span class="mailbox-attachment-icon has-img">
			<img src="{{imgsrc}}" alt="Attachment">
		</span>
		<div class="mailbox-attachment-info">
			<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
			<a href="{{fullName}}" class="btn btn-default btn-xs pull-right delbtn">
				<i class="fa fa-fw fa-remove"></i>	
			</a>
		</div>
	</li>
</script> 
<script>
//밑에 반복해서 만들어놓은 데이터를 template에  담음 
var template=Handlebars.compile($("#template").html());

$(".fileDrop").on("dragenter dragover", function(event){
	
	event.preventDefault(); //기본 동작을 막음
});

$(".fileDrop").on("drop",function(event){
	
	event.preventDefault();
	//drop된 파일을 dom 객체르 생성 
	var files=event.originalEvent.dataTransfer.files;
	
	var file=files[0];
	// formData 객체 생성
	var formData=new FormData();
	// formDate에 dom객체를 추가해준다는 의미.
	formData.append("file",file);
	
	$.ajax({
		url:"/uploadAjax",
		dataType:"text",
		data:formData,
		processData:false,
		contentType:false,
		type:"POST",
		success:function(data){
			
			//파일 정보를 받아오고
			var fileInfo=getFileInfo(data);
			//tempate에 넣어주고 
			var html= template(fileInfo);
			//uploadedList html태그 추가 해서 출력
			$(".uploadedList").append(html);
		}
	});
});

$("#registerForm").submit(function(){
	
	event.preventDefault();
	
	var that = $(this);
	var str="";
	$(".uploadedList .delbtn").each(function(index){
		
		str+="<input type='hidden' name='files["+index+"]' value='"+$(this).attr("href")+"'>";
	});
	
	that.append(str);
	that.get(0).submit();
});
</script>

<!--  파일 드래그앤 드랍으로 업로드할 form -->
<%@ include file="../include/footer.jsp" %>
