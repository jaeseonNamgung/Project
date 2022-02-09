<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
					alert("북마크 추가!!")
				}
			}
		}
	</script>
</head>
<body>
	<div id="title2">랜덤</div>
	
	<a href="random.do">
		<img class="random" src="${pageContext.request.contextPath }/resources/img/random.png">	
	</a>
	
	<a href="search_form.do">
		<img class="search" src="${pageContext.request.contextPath }/resources/img/search.png">	
	</a>
	
	<a href="bookmark_form.do">
		<img class="bookmark" src="${pageContext.request.contextPath }/resources/img/bookmark.png">	
	</a>	
	
	<a href="main2.do">
		<img class="home" src="${pageContext.request.contextPath }/resources/img/home.png">	
	</a>				
					
	
			
		<div id="photo_type2">
		
			<div><input type="hidden" name="idx" value="${ vo2.idx }"></div>
			
			<div class="img">
				<a href="toon_click.do?idx=${ vo2.idx }">
					<img src="${ pageContext.request.contextPath }/resources/upload/${ vo2.filename }"
					 width="400" height="400">		 
				</a>
			</div>
			
			<div class="info">
				<div>제목 : ${ vo2.title } </div>
				<div>작가 : ${ vo2.author } </div>
				<div>장르 : ${ vo2.genre } </div>
			</div>
					
			<div align="center">
			<input type="button" value="☆" onclick="bookmark(${vo2.idx});">
			<div>
			
		</div>

</body>
</html>