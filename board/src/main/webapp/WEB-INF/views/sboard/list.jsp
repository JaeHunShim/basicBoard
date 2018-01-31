<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/header.jsp" %>

<script>
	var result = '${msg}';

	if (result == 'SUCCESS') {
		alert("등록에 성공했습니다. ");
	}
</script>
<!-- 검색버튼 클릭시 이벤트와 쓰기페이지로 이동 이벤트  -->
<script>
$(document).ready(function(){
	//url주소뒤에 붙는 값을 1로 초기하하는 부분의 쿼리
	$("#searchBtn").on("click",function(){
		self.location="list"+'${pageMaker.makeQuery(1)}'
			+"&searchType="
			+$("select option:selected").val()
			+"&keyword="+encodeURIComponent($('#keywordInput').val());
	});
	$("#newBtn").on("click",function(){
		self.location="/board/register";
	});
});

</script>
<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class='box'>
				<div class="box-header with-border">
					<h3 class="box-title">Board List</h3>
				</div>
				<div class='box-body'>
				<select name="searchType">
						<option value="n" <c:out value="${cri.searchType == null?'selected':''}"/>>---</option>
						<option value="t" <c:out value="${cri.searchType eq 't'?'selected':''}"/>>Title</option>
						<option value="c" <c:out value="${cri.searchType eq 'c'?'selected':''}"/>>Content</option>
						<option value="w" <c:out value="${cri.searchType eq 'w'?'selected':''}"/>>Writer</option>
						<option value="tc" <c:out value="${cri.searchType eq 'tc'?'selected':''}"/>>Title OR Content</option>
						<option value="cw" <c:out value="${cri.searchType eq 'cw'?'selected':''}"/>>Content OR Writer</option>
						<option value="tcw" <c:out value="${cri.searchType eq 'tcw'?'selected':''}"/>>Title OR Content OR Writer</option>
				</select>
					<input type="text" name="keyword" id="keywordInput" value='${cri.keyword }'>
					<button type="button" class="btn btn-info" id="searchBtn">Search</button>
					<button type="button" class="btn btn-default"id="newBtn">New Board</button>
				</div>
			</div>
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">LIST PAGING</h3>
				</div>
				<div class="box-body">
					<h3 class="box-title">게 시 판</h3>
					<table class="table table-bordered">
						<tr style="border: 5px; border-color: black">
							<th>번호</th>
							<th>제목</th>
							<th>글쓴이</th>
							<th>작성날짜</th>
							<th>조회수</th>
						</tr>
						<c:forEach items="${list}" var="boardVO">
							<tr>
								<td>${boardVO.bno}</td>
								<%-- <td><a href="/board/read?bno=${boardVO.bno}">${boardVO.title}</a></td> --%>
								<td><a href="/board/readPage${pageMaker.makeSearch(pageMaker.cri.page) }&amp;bno=${boardVO.bno}">${boardVO.title }
										<strong>[${boardVO.replycnt}]</strong></a></td>
								<td>${boardVO.writer}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${boardVO.regdate}" /></td>
								<td><span class="badge bg-red">${boardVO.viewcnt}</span></td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<!-- /.box-body -->
				<div class="box-footer">
					<div class="text-center">
						<ul class="pagination">
							<c:if test="${pageMaker.prev}">	<!-- &laquo "<<"이거 표시 --> 
<!-- 검색기능 구현전					<li><a href="list${pageMaker.makeQuery(pageMaker.startPage-1) }">&laquo;</a></li><!--prev를 눌르면 보여지는 page대한 처리  -->
								<li><a href="list${pageMaker.makeSearch(pageMaker.startPage-1) }">&laquo;</a></li>
							</c:if>
							<!-- 페이지 번호출력에 대한 부분  반복문으로  -->
							<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="idx">
								<li
								 <c:out value="${pageMaker.cri.page==idx?'class=active':'' }"></c:out>>
<!--							 <a href="list?page=${idx }">${idx }</a>  검색기능 전 -->	
								 <a href="list${pageMaker.makeSearch(idx)}">${idx }</a>
								 </li>
							</c:forEach>
							
							<c:if test="${pageMaker.next && pageMaker.endPage >0 }"> 
<!-- 검색기능 구현전				<li><a href="list${pageMaker.makeQuery(pageMaker.endPage+1) }">&raquo;</a></li><!--next를 눌르면 보여지는 page대한 처리  -->
							<li><a href="list${pageMaker.makeSearch(pageMaker.endPage+1) }">&raquo;</a></li>
							</c:if>
						</ul>
					</div>
				</div>
				<!-- /.box-footer-->
			</div>
		</div>
		<!--/.col (left) -->
	</div>
	<!-- /.row -->
</section>
<!-- /.content -->
<%@include file="../include/footer.jsp"%>

