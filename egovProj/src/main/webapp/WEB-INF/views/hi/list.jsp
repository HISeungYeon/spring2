<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="card card-primary">
	<div class="card-header">
		<h4 class="card-title">Ekko Lightbox</h4>
	</div>
	<div class="card-body">
		<div class="row">
		<c:forEach var="authVO" items="${bookVO.bookAuthVOList}">
			<div class="col-sm-2">
				<a href="#modal-default" class="btn btn-modal"
					data-toggle="modal" 
					data-title="sample 1 - white"
					data-target="#modal-default"> 
					<img
					src="/resources/upload/moon.png"
					class="img-fluid mb-2" alt="white sample">
				</a>
			</div>
		</c:forEach>
		</div>
	</div>
</div>

<!-- 모달 시작 -->
<div class="modal fade" id="modal-default" style="display: none;"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Default Modal</h4>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body">
				<p>One fine body…</p>
			</div>
			<div class="modal-footer justify-content-between">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save changes</button>
			</div>
		</div>

	</div>

</div>
<!-- 모달 끝! -->