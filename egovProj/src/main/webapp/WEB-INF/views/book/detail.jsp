<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>

<form name="frm" id="frm" method="post">
	<div class="card card-primary">
			<div class="card-header">
				<h3 class="card-title">도서 정보</h3>
			</div>
			<div class="card-body">
		
				<div class="form-group">
					<label>도서 번호</label>
						<input type="text" class="form-control" name="bookId" value="${bookVO.bookId}" readonly>
				</div>
				
				<div class="form-group">
					<label>도서 제목</label>
						<input type="text" class="form-control hi" name="title" value="${bookVO.title}" readonly>
				</div>
				
				<div class="form-group">
					<label>카테고리</label>
						<input type="text" class="form-control hi" name="category" value="${bookVO.category}" readonly>
				</div>
				
				<div class="form-group">
					<label>도서 가격</label>
						<input type="text" id="price" class="form-control hi" name="price" value="<fmt:formatNumber value="${bookVO.price}" pattern='#,###' />" readonly>
				</div>
				
				<div class="form-group">
					<label>도서 내용</label>
					<textarea id="content" name="content" class="form-control hi" rows="10" readonly>${bookVO.content}</textarea>
				</div>
				
				<div class="form-group">
					<label>등록 날짜</label>
					<input type="text" class="form-control datetimepicker-input"
							data-target="#reservationdatetime" value="<fmt:formatDate value="${bookVO.insertDate}" pattern='yyyy-MM-dd'/>" readonly>
				</div>
				<div id="spn1" align="right">
					<button type="button" id="edit" class="btn btn-outline-warning" >수정</button>
					<a href="/book/list" class="btn btn-outline-secondary">목록</a>
				</div>
				<div id="spn2" align="right" style="display:none">
					<button type="submit" class="btn btn-outline-success">확인</button>
					<a href="/book/detail?bookId=${bookVO.bookId}" class="btn btn-outline-danger">취소</a>
				</div>
			</div>
	</div>
</form>

<script type="text/javascript">
$(function(){
// 	alert("하이루");
	
	//수정버튼 클릭 -> 수정모드로 전환
	$("#edit").on("click", function(){
		//일반모드 가리고
		$("#spn1").css("display", "none");
		//수정모드 전환
		$("#spn2").css("display", "block");
		
		$(".hi").removeAttr("readonly");
		
		$(".hi").attr("required", true);
		//책 내용 readonly 풀어주기
		CKEDITOR.instances['content'].setReadOnly(false);		
		//form action 추가
		$("#frm").attr("action", "/book/updatePost");
		
	});
	
	//숫자입력 validation
	$("#price").on("keyup",function(){
		$(this).val($(this).val().replace(/[^0-9]/g,""));
		});

	});
	
	CKEDITOR.replace("content");  
</script>
