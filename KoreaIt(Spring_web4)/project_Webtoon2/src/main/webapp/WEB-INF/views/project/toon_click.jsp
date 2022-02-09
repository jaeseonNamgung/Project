<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

	<!-- Ajax를 위한 httpRequest.js참조 -->
	<script src="${ pageContext.request.contextPath }/resources/js/httpRequest.js"></script>
	
	<link rel="stylesheet" 
	href="${ pageContext.request.contextPath }/resources/css/search.css">
	
	<script>
		function rec_plus(r){
			var idx = r;
			var url = "rec_plus.do";
			var param = "idx=" + idx;
			sendRequest(url, param, resultFn_plus, "POST");
		}
		
		function resultFn_plus(){

			if(xhr.readyState == 4 && xhr.status == 200){
				
				var data = xhr.responseText;
				
				if(data == "yes"){
					alert("추천수 +1!!");					
				}
				
			}
		}
		
		function rec_minus(r){
			
			var idx = r;
			var url = "rec_minus.do";
			var param = "idx=" + idx;
			sendRequest(url, param, resultFn_minus, "POST");
		}
		
		function resultFn_minus(){

			if(xhr.readyState == 4 && xhr.status == 200){
				
				var data = xhr.responseText;
				
				if(data == "yes"){
					alert("추천수 -1!!");					
				}
				
			}
		}
		
		function bookmark(i){
			
			if(!confirm("북마크에 추가하시겠습니까?")){
				return;
			}
			
			var toon_idx = i;
				
			var url = "bookmark.do";
			var param = "toon_idx=" + toon_idx;
			sendRequest(url, param, resultFn, "POST");
		}
		
		function resultFn(){
			
			if(xhr.readyState == 4 && xhr.status == 200){
				
				var data = xhr.responseText;
			
				if(data == "logout"){
					alert("로그인 하십시오.");
					return;
				}
				
				if(data == 'no'){
					alert("북마크에 있는 만화입니다.");
					return;
				}
				else{
					alert("북마크 추가!!");
				}
				
			}
		}
	</script>
	
</head>
<body>
	<div id="title3">웹툰 정보</div>
	
	<img class="recup" src="${pageContext.request.contextPath }/resources/img/recup.png"
		 onclick="rec_plus(${vo.idx});">	
		 
	<a href="search_form.do">
		<img class="search2" src="${pageContext.request.contextPath }/resources/img/search.png">	
	</a>	
	
	<a href="main2.do">
		<img class="home2" src="${pageContext.request.contextPath }/resources/img/home.png">	
	</a>	
	
	<a href="bookmark_form.do">
		<img class="bookmark2" src="${pageContext.request.contextPath }/resources/img/bookmark.png">	
	</a>	
			 
	<img class="recdown" src="${pageContext.request.contextPath }/resources/img/recdown.png"
		 onclick="rec_minus(${vo.idx});">	

	
		<div id="photo_type22">
			<div><input type="hidden" name="idx" value="${ vo.idx }"></div>
			<img src="${ pageContext.request.contextPath }/resources/upload/${ vo.filename }"
				 width="400" height="400">
			
			<div class="info">
				<div>제목 : ${ vo.title } </div>
				<div>작가 : ${ vo.author } </div>
				<div>장르 : ${ vo.genre } </div>
			</div>
		
		<div align="center">
			<input type="button" value="보러가기" 
			   onclick="location.href='${ vo.website}'">								
			<input type="button" value="☆" onclick="bookmark(${vo.idx});">
		</div>
	
</body>
</html>