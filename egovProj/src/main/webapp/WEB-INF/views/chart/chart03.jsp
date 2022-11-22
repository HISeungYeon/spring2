<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>제이쿼리로 동적으로 생성된 버튼에 이벤트를 달아보쟈 </title>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript">
$(function(){
	$("#firstButton").on("click",function(e){
		let bodyHtml = "<button type='button' id='secondButton'>두 번째 버튼(동적생성)</button>";
		//mainDiv 마지막 요소로 추가
		$("#mainDiv").append(bodyHtml);
	});
	
	//두 번째 버튼 이벤트
	//두 번째 버튼을 클릭하면 alert("개동이");
	$("#secondButton").on("click",function(e){
		alert("하이루");
	});
	
	$(document).on("click","#secondButton",function(){-
		alert("하이루");
	});
	
	
});
</script>
<script type="text/javascript">
	//쉼표 문자열에서 최대값 구해보기
	let c_values = "500,200,600,700,100,300";
	let hi = c_values.split(',');
	let max = Math.max.apply(null,hi);
	
	console.log("max는?? " + max)
	
</script>

</head>
<body>
	<div id="mainDiv">
		<button id="firstButton">첫 번째 버튼</button>
	</div>
<script type="text/javascript">
   let key2 = sessionStorage.getItem("key2");
   
   console.log("key2 : " + key2);
</script>
</body>
</html>