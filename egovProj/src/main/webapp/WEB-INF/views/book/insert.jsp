<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%> 
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>

<form name="frm" id="frm" method="post" action="/book/insert">
	<div class="card card-primary">
		<div class="card-header">
			<h3 class="card-title">도서 추가</h3>
		</div>
		<div class="card-body">

			<div class="form-group">
				<label>도서 번호</label> <input type="text" class="form-control"
					name="bookId" value="0" readonly>
			</div>

			<div class="form-group">
				<label>도서 제목</label> <input type="text" class="form-control hi"
					name="title">
			</div>

			<div class="form-group">
				<label>카테고리</label> <select
					class="form-control select2 select2-hidden-accessible"
					style="width: 100%;" data-select2-id="1" tabindex="-1"
					aria-hidden="true" name="category">
					<option selected="selected" data-select2-id="3">소설</option>
					<option data-select2-id="35">자기계발</option>
					<option data-select2-id="36">에세이</option>
					<option data-select2-id="37">시</option>
					<option data-select2-id="38">동화</option>
					<option data-select2-id="39">역사</option>
					<option data-select2-id="40">철학</option>
				</select>
			</div>

			<div class="form-group">
				<label>도서 가격</label> <input type="text" id="price"
					class="form-control hi" name="price">
			</div>

			<div class="form-group">
				<label>도서 내용</label>
				<textarea id="content" name="content" class="form-control" rows="4">
					
				</textarea>
			</div>

			<div id="spn1" align="right">
				<button type="submit" class="btn btn-outline-success">확인</button>
				<a href="/book/list" class="btn btn-outline-secondary">취소</a>
			</div>
		</div>
	</div>
	<sec:csrfInput/>
</form>

<script type="text/javascript">
	$(function() {
		// 	alert("하이루");
		//숫자입력 validation
		$("#price").on("keyup", function() {
			$(this).val($(this).val().replace(/[^0-9]/g, ""));
		});

	});
	CKEDITOR.replace("content");  
</script>
