<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<title>Insert title here</title>
<script>
var bno=2;
//RestController의 경우는 객체를 json형태로 전달하기 때문에 받을때 getJSon()을 이용해야 한다. 
$.getJSON("/replies/all/"+bno,function(data){ 
	console.log(data.length);
});
</script>
</head>
<body>
	<h2> Ajax Test Page</h2>
	<ul id="replies"></ul> <!-- 결과는  bno를 PathVariable 데이터를 컨트롤러에서 받아서 처리를 하게 된다. console로 직으면 bno에 맞는 데이터들을 가지온다  -->
</body>
</html>