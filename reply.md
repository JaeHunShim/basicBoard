댓글 등록,수정,삭제
===
* ### 댓글 등록부분

      <div class="box-footer">
            <button type="submit" class="btn btn-info" id="replyAddBtn">댓글등록하기</button>
      </div>
replyAddBtn을  클릭하면  

      <div class="box-body">
          <label for="newReplyWriter">글쓴이</label>
            <input class="form-control" type="text" placeholder="user_id" id="newReplyWriter">
          <label for="newReplyText"> 댓글내용</label>
            <textarea class="form-control" placeholder="reply_text" rows="3" id="newReplyText"></textarea>
        </div>
밑에 있는 해당 id 값의 val()값을  가지고와서 replyer,replytext 변수에 넣고

        $("#replyAddBtn").on("click",function(){
        var replyerObj = $("#newReplyWriter");
        var replytextObj = $("#newReplyText");

        var replyer = replyerObj.val();
        var replytext= replytextObj.val();

  ajax 처리로 url 해당컨트롤러로 보내고 등록하고 성공했을때 현재 페이지 번호는 1로 하고 ,댓글 페이지를 조회함.(기존 board페이지를 유지 )

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

* ### 댓글 수정
      $(".timeline").on("click",".replyLi", function(){
      		var reply =$(this); //해당 replyLi

      		$("#replytext").val(reply.find(".timeline-body").text());
      		$(".modal-title").html(reply.attr("data-rno")); //<h4>태그의 modal-title에 rno 값을 추가

      	});
밑에 반복되는 코드에서 replytext 값과 현재 data-rno를 .modal-title에 추가(attr)한다. 이유는 수정할때나 삭제 할때 모두 rno를 controller에서 받아서 ajax처리 하기 때문에 값을 받아온다.
받아온 값을

      var rno= $(".modal-title").html();
      var replytext=$("#replytext").val();
  변수 rno와 replytext 에 넣고  ajax데이터를 controller에 보내서 처리하게 된다.
  여기서 주의점은 type은 put방식이고 데이터를 보내는 거니가 data를 JSON형태로 보내야 한다는 점이다.

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

삭제도 마찬가지의 형식으로 돌아가게 된다.

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
  다른점은 type이 delete인 것과 보낼 데이터없이 현재 url에서 가져온 rno정보를 가지고 해당 댓글을 삭제하는 것이 다르다.
