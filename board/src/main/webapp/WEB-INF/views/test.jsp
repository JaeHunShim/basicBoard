<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<style type="text/css">
#modDiv {
	width: 300px;
	height: 100px;
	background-color: gray;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -50px;
	margin-left: -150px;
	padding: 10px;
	z-index: 1000;
	
}
.pagination {
  width: 100%;
}

.pagination li{
  list-style: none;
  float: left; 
  padding: 3px; 
  border: 1px solid blue;
  margin:3px;  
}

.pagination li a{
  margin: 3px;
  text-decoration: none;  
}


</style>

<title>Insert title here</title>
<script>
//RestController의 경우는 객체를 json형태로 전달하기 때문에 받을때 getJSon()을 이용해야 한다.
//뷰에 보여질 ajax데이터를 받아서 처리 하는 부분을 함수로 만들어놓음 
//뎃글 목록보는 view 만들기 
var bno=2
	getPageList(1);
	

function getAllList(){ //함수로 만들어 놓음 
	
	
	$.getJSON("/replies/all/"+bno,function(data){
		var str="";
		console.log(data.length);  //console 창보면 데이터 갯수출력나온다
		
		$(data).each(function(){ // li태그 안에 "data-"로 시작하는 속성은 이름이나 갯수에 관계없이 태그내에서 자유롭게 사용할수 있어서 id나 name보단 편함 " 
					str += "<li data-rno='"+this.rno+"' class='replyLi'>"
						+this.rno+":"+this.replytext
						+"<button>수정</button>"
						+"</li>";
		});
		
		$("#replies").html(str);//밑에 <ul id="#replies">여기에 str에 저장되어있는 태그들과 정보를 뿌려주게 된다. </ul>
	});
}
//페이징 view만들기 (10개씩 보여지게 하는 부분)
function getPageList(page){
	$.getJSON("/replies/"+bno+"/"+page , function(data){ //Json형태로  데이터를 가져오는 부분 
		
		console.log(data.list.length);
		var str="";
		
		$(data.list).each(function(){
			str+= "<li data-rno='"+this.rno+"' class='replyLi'>"
			+this.rno+":"+this.replytext
			+"<button>수정</button></li>";
		});
		$("#replies").html(str); 
		
		printPaging(data.pageMaker);
	});
}
//페이징처리 하단 부분  만드는 부분
function printPaging(pageMaker){
	
	var str="";
	
	if(pageMaker.prev){
		str+="<li><a href='"+(pageMaker.startPage-1)+"'> << </a></li>";
	}
	for(var i=pageMaker.startPage,len=pageMaker.endPage; i<=len; i++){
		var strClass= pageMaker.cri.page==i?'class=active':'';
		str+= "<li "+strClass+"><a href='"+i+"'>"+i+"</a></li>";
	}
	if(pageMaker.next){
		str+= "<li><a href='"+(pageMaker.endPage+1)+"'> >> </a></li>";
	}
	$(".pagination").html(str)
}
//댓글 등록부분 
$(document).ready(function(){

	$("#addReplyBtn").on("click",function(){
		
		var replyer= $("#newReplyWriter").val(); //태그안에 해당 아이로 된 name값을 가지고 온다 
		var replytext= $("#newReplyText").val();
		
		$.ajax({
			type:"post",  //post방식으로 데이터 전송
			url:"/replies", //url주소 
			headers:{
				"Content-Type":"application/json", //HTTP 헤더 정보에 json형태의 타입을 사용하겠다고 명시 해줘야함 
				"X-HTTP-Method-Override":"POST"
			},
			dataType:"text",
			data:JSON.stringify({ //json데이터라는 걸 명시 
				bno:bno,
				replyer:replyer,
				replytext:replytext
			}),
			success:function(result){
				if(result=='SUCCESS'){
					alert("등록이 되었습니다");
					getPageList(replyPage); //게시물 등록한후에 전체 댓글에 대한 목록갱신
					
				}
			}
		});
	});
	
	//댓글 수정창 띄우는 부분
	$("#replies").on("click", ".replyLi button",function(){
		
		var reply=$(this).parent(); //.parent(): 해당요소의 바로위에 존재하는 부모요소를 반환한다. 즉 상위 태그인 li태그에서 필요한 데이터를뽑아온다는 소리 
									//코드의 중복을 막기위해서 (this를 사용했음 )
		var rno=reply.attr("data-rno");  // 데이터 값을 추출해 오는 부분 
		var replytext=reply.text();
		
		$(".modal-title").html(rno);
		$("#replytext").val(replytext);
		$("#modDiv").show("slow");
	});
	//댓글 삭제 처리 AJAX
	$("#replyDelbtn").on("click",function(){
		
		var rno= $(".modal-title").html(); // modal-title클래스에 있는 rno 데이터를 가지고옴 
		var replytext= $("#replytext").val(); //input에 있는 replytext값을 가지고 옴 
		
		$.ajax({
			type:"delete",
			url:'/replies/'+rno, // url을 보낼때 rno도 같이 보낸다  그게 주소가 되면서 파라미터가 된다. 
			headers:{
				"Content-Type":"application/json",					
				"X-HTTP-Method-Override":"DELETE" // 컨트롤러 보면 delete로 받게 되어있음 
			},
			dataType:"text",
			success:function(result){
				console.log("result:"+result);
				if(result=="SUCCESS"){
					alert("삭제되었습니다.");
					$("#modDiv").hide("slow");
					getPageList(replyPage);
				}
			}
		});
	});
	//댓글 수정처리 
	$("#replyModbtn").on("click",function(){
		
		var rno=$(".modal-title").html();
		var replytext=$("#replytext").val();
		
		$.ajax({
			type:'put',
			url:"/replies/"+rno,
			headers:{
				"Content-Type":"application/json",
				"X-HTTP-Method-Override":"PUT"
			},
			dataType:"text",
			data:JSON.stringify({replytext:replytext}), //수정처리와 비슷하지만 여기서는 데이터를 JSON형태로 넘겨조야 하기때문에 data에 JSON형태로 보내게함 
			
			success:function(result){
				console.log("result"+ result);
				
				if(result=="SUCCESS"){
					alert("수정되었습니다.");
					$("#modDiv").hide("slow");
					//getAllList();
					getPageList(replyPage);
					
				}
			}
		});
	});
	var replyPage=1;
	$(".pagination").on("click","li a", function(event){
			
		event.preventDefault(); 
		replyPage=$(this).attr("href");
		getPageList(replyPage);		
	});
});
</script>
</head>
<body>
	<h2> Ajax Test Page</h2>
	
	<div>
		<div>
			Replyer<input type="text" name="replyer" id='newReplyWriter'>
		</div>
		<div>
			ReplyText<input type="text" name="replytext" id="newReplyText">
		</div>
		<button id="addReplyBtn">add reply</button>
	</div>
	<div id="modDiv" style="display:none;">
		<div class="modal-title"></div>
		<div>
			<input type="text" id="replytext">
		</div>
		<div>
			<button type="button" id="replyModbtn"> 수정</button>
			<button type="button" id="replyDelbtn"> 삭제</button>
			<button type="button" id="closebtn"> 닫기</button>
		</div>
	</div>
	<ul id="replies"><!-- 댓글 목록 나오는 부분(페이징처리해서   -->
	
	</ul>
	
	<ul class="pagination"><!--  페이징 처리 하단 부분 -->
	
	</ul>
</body>
</html>