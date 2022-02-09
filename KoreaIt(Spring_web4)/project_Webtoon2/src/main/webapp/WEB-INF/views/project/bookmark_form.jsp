<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

	<link rel="stylesheet" 
	href="${ pageContext.request.contextPath }/resources/css/search.css">
	
	<script>
		function bookmark_delete(f){
			
			var toon_idx = f;
			
			if(!confirm("정말 삭제하시겠습니까?")){
				return;
			}
			
			alert("삭제 성공!!")
			location.href="bookmark_delete.do?toon_idx=" + toon_idx;
			
			
		}
	</script>
</head>
<body>
	<div id="title4">북마크</div>
	
	<a href="search_form.do">
		<img class="plus" src="${pageContext.request.contextPath }/resources/img/plus.png">	
	</a>	
	
	<a href="main2.do">
		<img class="home3" src="${pageContext.request.contextPath }/resources/img/home.png">	
	</a>	
	
	<form>
		<div id="toon_box2">
			<c:forEach var="vo" items="${ list }">
				<div class="photo_type">
					
					<img src="${ pageContext.request.contextPath }/resources/upload/${ vo.filename }"
					 width="150" height="200" class="img2">
					
						<div class="title">제목 : ${ vo.title } </div>
						<div class="author">작가 : ${ vo.author } </div>
						<div class="genre">장르 : ${ vo.genre } </div>
					
					<div align="center">
						<input type="button" value="보러가기" onclick="location.href='${vo.website}'">
						<input type="button" value="☆ 삭제" onclick="bookmark_delete(${vo.idx});">
					</div>
					
				</div>
			</c:forEach>
		</div>
	</form>
	
</body>
</html>