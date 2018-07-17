<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/resources/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/font-awesome.min.css" media="screen" title="no title">
<link rel="stylesheet" type="text/css" href="/resources/bootstrap/css/style.css" media="screen" title="no title">
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script src="/resources/js/bootstrap/bootstrap.min.js"></script>
<title>Insert title here</title>
<script>
	$(document).ready(function(){
		$('#upw').keyup(function(){
			$('font[name=check]').text('');	
		});
		$('#upw2').keyup(function(){
			if($('#upw').val()!=$('#upw2').val()){
				$('font[name=check]').text('');
				$('font[name=check]').html('암호가 일치 하지 않습니다.');
			}
			else{
				$('font[name=check]').text('');
				$('font[name=check]').html('암호가 일치 합니다.');
			}
		});
	});
</script>
</head>
<body>

 <article class="container">
        <div class="page-header">
        	<div class="modal-header">
          <h1>회원가입 <small>basic form</small></h1>
          	</div>
        </div>
        <div class="modal-body">
        <div class="col-md-6 col-md-offset-3">
          <form role="form" method="post" action="/user/join">
            <div class="form-group">
              <label for="InputEmail">회원 아이디</label>
              <input type="text" class="form-control" name="uid" placeholder="아이디를 입력해주세요">
            </div>
            <div class="form-group">
              <label for="InputPassword1">비밀번호</label>
              <input type="password" class="form-control" name="upw" id= "upw" placeholder="비밀번호 입력해주세요">
            </div>
            <div class="form-group">
              <label for="InputPassword2">비밀번호 확인</label>
              <input type="password" class="form-control" id="upw2" placeholder="비밀번호 확인">
              <font name='check' size='3' color='red'></font>
            </div>
            <div class="form-group">
              <label for="username">이름</label>
              <input type="text" class="form-control" name="uname" placeholder="이름을 입력해 주세요">
            </div>
            <div class="form-group">
              <label for="InputEmail">E-Mail</label>
              <input type="email" class="form-control" name="email" placeholder="이메일을 입력해주세요">
            </div>
            <div class="modal-footer">
            <div class="form-group text-center">
              <button type="submit" class="btn btn-info">회원가입<i class="fa fa-check spaceLeft"></i></button>
              <button type="submit" class="btn btn-warning">가입취소<i class="fa fa-times spaceLeft"></i></button>
            </div>
            </div>
          </form>
        </div>
        </div>
       </article>
</body>
</html>