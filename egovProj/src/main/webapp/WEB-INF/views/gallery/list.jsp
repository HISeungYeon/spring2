<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<div class="col-12">
	<div class="card card-primary">
		<div class="card-header">
			<!-- 도서 선택 시작 -->
			<!-- ajax를 통해 append <option value="1">검은개똥이</option> -->
			<select class="custom-select">
	         </select>
			<!-- 도서 선택 끝 -->
		</div>
		<div class="card-body">
			<div class="row">
				<c:forEach var="bookAuthVO" items="${bookVO.bookAuthVOList}">
					<div class="col-sm-2">
						<a href="#modal-default" class="btn btn-modal" 
							data-toggle="modal"
							data-id="/resources/upload${bookAuthVO.filename}"
							data-title="${bookVO.title}"
							data-userno="${bookVO.bookId}"
							data-seq="${bookAuthVO.seq}"
							data-filename="${bookAuthVO.filename}" > 
							<img
							src="/resources/upload${bookAuthVO.filename}" class="img-fluid mb-2"
							alt="white sample">
						</a>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>

<!-- Modal 시작 -->
<div class="modal fade" id="modal-default" style="display: none;"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">${bookVO.title}</h4>
				<input type="hidden" id="txtUserId" value="" />
				<input type="hidden" id="txtSeq" value="" />
				<input type="hidden" id="txtFilename" value="" />
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body">
				<p id="body-content">하이루</p>
			</div>
			<div class="modal-footer justify-content-between">
				<div style="float:Left;">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
				<div style="float:right;">
					<span id="spn1">
						<a class="btn btn-app" onclick="fn_download()">
							<i class="fas fa-save"></i> Save
						</a>
						<button type="button" id="edit" class="btn btn-outline-warning">수정</button>
						<button type="button" id="delete" class="btn btn-outline-secondary">삭제</button>
					</span>
					<span id="spn2" style="display:none;">
						<div class="input-group justify-content-between">
							<div class="custom-file" align="left" style="margin-right:10px">
								<input type="file" class="custom-file-input" id="uploadFile" name="uploadFile" style="width:200px;">
								<label class="custom-file-label" for="exampleInputFile">Choose file</label>
							</div>
							<div align="right">
								<button type="button" id="uploadBtn" class="btn btn-outline-info">확인</button>
								<button type="button" id="cancel" class="btn btn-outline-secondary">취소</button>
							</div>
						</div>
					</span>
				</div>
			</div>
		</div>
	</div>
</div>
<iframe id="ifrm" name="ifrm" style="display:none;"></iframe>
<!-- Modal 끝 -->
<script type="text/javascript">
	$(function() {
		//data-id="....."
		$(".btn-modal").click(
				function() {
					let data = $(this).data("id");
					let title = $(this).data("title");
					//userId랑 seq는 ATTACH 테이블의 복합키 (composite key)로써의 기본키(primary key, 식별키)
					//data-userId="...."
					let userNo = $(this).data("userno");
					//data-seq="..."
					let seq = $(this).data("seq");
					//data-filename="....
					let filename = $(this).data("filename");
					
					//세션 스토리지 활용
					sessionStorage.setItem("filename",filename);
					
					console.log("data가 모게  " + data + " title은?? " + title + 
								" userNo는?? " + userNo + "seq는?? " + seq +
								"filename은? " + filename);
					
					$("#body-content").html(
							"<img src='" + data + "' style='width:100%;' />");
					$(".modal-title").text(title);
					$("#txtUserId").val(userNo);
					$("#txtSeq").val(seq);
					$("#txtFilename").val(filename);
					
					
		});
		//el 정보를 j/s 변수에 담음
		let currentBookId = "${param.bookId}";
		let sel = "";

		//도서 목록 가져와서 select에 추가하기
		$.ajax({
			url:"/gallery/bookList",
			datatype:"json",
			type: "get",
			success:function(data){
// 				console.log("data는?? " + JSON.stringify(data));
				
				$.each(data, function(index,item){
// 					console.log("item.bookId : " + item.bookId);
// 					console.log("item.title : " + item.title);
					
					if(item.bookId == currentBookId){
						sel = "selected='selected'"
					}else{
						sel = "";
					}
					//option항목 추가
					$(".custom-select").append("<option value='"+ item.bookId +"'"+sel+">"+ item.title +"</option>");
					
				});
			}
			
		});
		
		
		//.custom-select가 바뀌면 이미지 목록을 다시 가져옴
		$(".custom-select").on("change", function(){
			//선택된 option의 value를 가져와보쟈
			let bookId = $(this).val();
// 			console.log("bookId : " + bookId);
			
			location.href="/gallery/list?bookId="+bookId;
		});
		
		$("#edit").on("click", function(){
			//일반모드 가리고
			$("#spn1").css("display", "none");
			//수정모드 전환
			$("#spn2").css("display", "block");
			
		});
		
		$("#cancel").on("click", function(){
			//일반모드 전환
			$("#spn1").css("display", "block");
			//수정모드 가리기
			$("#spn2").css("display", "none");
		});
		
		//ajax 파일 업로드 구현 시작
		//이미지 체킹
		let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$"); //정규식
		let maxSize = 5242880; //5MB
		
		function checkExtension(fileName, fileSize){ //확장자, 크기 체킹
			if(fileSize >= maxSize){
				alert("파일 사이즈가 초과되었습니다.");
				//함수 종료
				return false;
			}
			
			//filename : 개똥이.zip
			if(regex.test(fileName)){
				alert("해당 종류의 파일은 업로드할 수 없습니다.");
				return false;
			}
			return true;
		}
		
		//ajax 파일 업로드 구현 시작
		$("#uploadBtn").on("click",function(e){
			//가상의 form 태그 생성 <form></form>
			let formData = new FormData();
			//<input type="file" name="uploadFile"..
			let inputFile = $("input[name='uploadFile']");
			//파일 object 안에 이미지를 가져와보쟈
			//inputFile[0] : input type="file"
			//.files : 그 안에 들어있는 이미지
			let files = inputFile[0].files;
			
			console.log("files는?? " + files);
			
			//가상의 form인 formDate Object에 filedata를 추가해보쟈
			for(let i=0; i<files.length; i++){
				//파일 확장자 / 크기 체킹(확장자가 exe, sh, zip, alz인 경우 안돼!)
				if(!checkExtension(files[i].name, files[i].size)){
					return false;
				};
				
				
				//<form><input type='file' name='uploadFile' /></form>
				formData.append("uploadFile", files[i]);
			}
			
			/* 	<form>
					<input type='file' name='uploadFile' />
					<input type='text' name='userNo' value="3" />
					<input type='text' name='seq' value="5" />
				</form> 
			*/
			
			formData.append("userNo",$("#txtUserId").val());
			formData.append("seq",$("#txtSeq").val());
			
			console.log("formData : " + formData);
			
			//contentType: 보내는 데이터 타입
			//dataType: 받는 데이터 타입
			
			let url = "/gallery/updatePost";
			
			$.ajax({
				url:"/gallery/updatePost" ,
				processData:false,
				contentType:false,
				data:formData,
				dataType:"json",
				type:"post",
				success:function(result){
					console.log("result : " + JSON.stringify(result));
					console.log("uploaded");
					let filename = result.filename;
					//$("#body-content img").attr("src", "/resources/upload"+filename);
					alert("이미지 수정 성공!");
					location.href="/gallery/list?bookId=${param.bookId}";
					
				}
			});
		});
		//ajax 파일 업로드 구현 끝
		
		//이미지 미리보기 시작
		let sel_file = [];
		$("#uploadFile").on("change",handleImgFileSelect);
		//e : onchange 이벤트 객체
		function handleImgFileSelect(e){
			//이벤트가 발생 된 타겟 안에 들어있는 이미지 파일들을 가져와보쟈
			let files = e.target.files;
			//이미지가 여러개가 있을 수 있으므로 이미지들을 각각 분리해서 배열로 만듦
			let fileArr = Array.prototype.slice.call(files);
			//파일 타입의 배열 반복. f : 배열 안에 들어있는 각각의 이미지 파일 객체
			fileArr.forEach(function(f){
				if(!f.type.match("image.*")){
					alert("이미지 확장자만 가능해욤");
					//함수 종료
					return;
				}
				//이미지 객체를 전역 배열타입 변수에 넣쟈
				sel_file.push(f);
				//이미지 객체를 읽을 자바스크립트의 reader 객체 생성
				let reader = new FileReader();
				reader.onload = function(e){
					//e.target : f(이미지 객체)
					//e.target.result : reader가 이미지를 다 읽은 결과
					let img_html = "<img src=\"" + e.target.result + "\" style='width:100%;' />";
					//p 사이에 이미지가 렌더링 되어 화면에 보임
					//객체.append : 누적, .html : 새로고침, .innerHTML : J/S
					$("#body-content").html(img_html);
				}
				//f : 이미지 파일 객체를 읽은 후 다음 이미지 파일(f)을 위해 초기화 함
				reader.readAsDataURL(f);
			}); //end forEach
		}
		//이미지 미리보기 끝
		
		
		//이미지 삭제 시작
		$("#delete").on("click",function(){
			let userNo = $("#txtUserId").val();
			let seq = $("#txtSeq").val();
			
			console.log("userNo : " + userNo + " seq : " + seq);
			
			let data = {"userNo":userNo, "seq":seq};
			
			console.log("data : " + JSON.stringify(data));
			
			$.ajax({
				url:"/gallery/deletePost",
				contentType:"application/json;charset=utf-8",
				data:JSON.stringify(data),
				dataType:"json",
				type:"post",
				success:function(result){
					console.log("result : " + JSON.stringify(result));
// 					location.reload();
					//1또는 0이 할당됨.
					let str = result.result;
					if(str>0){
						location.href="/gallery/list?bookId=${param.bookId}";
					}else{
						alert("띠용 ㅇㅁㅇ 삭제 실패,,");
					}
					
				}
			});
		});
		
		//이미지 삭제 끝
		});
</script>
<script type="text/javascript">
//파일 다운로드 함수
function fn_download(){
	let filename = sessionStorage.getItem("filename");
	console.log("filename : " + filename);
	
	let vIfrm = document.getElementById("ifrm");
	vIfrm.src = "/download?fileName="+filename;
}

</script>