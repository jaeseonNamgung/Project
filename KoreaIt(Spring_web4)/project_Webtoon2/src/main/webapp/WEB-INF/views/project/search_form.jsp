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
	
	
	<!-- css참조 -->
	<link rel="stylesheet" 
	href="${ pageContext.request.contextPath }/resources/css/search.css">
	
	<script>
		function genre_put(g2){
			
			var genre = document.getElementById("g");
			genre.value = g2;
			
		}
		
		function order_put(o2){
			
			var order = document.getElementById("o")
			order.value = o2;
		}
	
		function send(f){
			
			var title = f.title.value.trim();
			var author = f.author.value.trim();
			var genre = f.genre.value.trim();
			var order = f.order.value.trim();
			
			alert(title+"/"+author+"/"+genre+"/"+order);
			
			f.action = "search.do";
			f.method = "post";
			f.submit();
			
		}
		
		function random(){
			
			
			location.href="random.do";
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
	
	<img class="logo_img2" src="${pageContext.request.contextPath }/resources/img/arin.png">	
	<p id="title"> 웹툰 검색 </p>
		
		 
	<form>
		<div id="search">
			<div>
				<p class="title2">제목 : </p>
				<input name="title" placeholder="제목을 입력하세요" class="title22">
			</div>
			
					
			<div>
				<p class="author2">작가 : </p>
				<input name="author" placeholder="작가명을 입력하세요" class="author22">
			</div>
					
			<div>
				<p class="genre2">장르 : </p>
				<input name="genre" type="text" value="전체" id="g" class="genre22" readonly>
				
				<div id="button1">
					<input type="button" value="전체" onclick="genre_put(value);">
					<input type="button" value="개그" onclick="genre_put(value);">
					<input type="button" value="드라마" onclick="genre_put(value);">
					<input type="button" value="로맨스" onclick="genre_put(value);">
					<input type="button" value="스릴러" onclick="genre_put(value);">
					<input type="button" value="스포츠" onclick="genre_put(value);">
					<input type="button" value="액션" onclick="genre_put(value);">
					<input type="button" value="판타지" onclick="genre_put(value);">
				</div>
			</div>
					
			<div>
				<p class="order2">정렬 : </p>
				<input name="order" type="text" value="기본" id="o" class="order22" readonly>
				
				<div id="button2">
					<input type="button" value="기본" onclick="order_put(value);">
					<input type="button" value="조회순" onclick="order_put(value);">
					<input type="button" value="추천순" onclick="order_put(value);">
				</div>
			</div>
				
			<div id="button3">
				<input type="button" value="검색" onclick="send(this.form);">
				<input type="button" value="랜덤" onclick="random();">
				<input type="button" value="북마크" onclick="location.href='bookmark_form.do'">
				<input type="button" value="메인" onclick="location.href='main2.do'">
			</div>
		</div>
	</form>
	
		<div id="toon_box">
			<c:forEach var="vo" items="${ list }">
				<div class="photo_type">
			
					<a href="toon_click.do?idx=${ vo.idx }">
						<img src="${ pageContext.request.contextPath }/resources/upload/${ vo.filename }"
						 width="150" height="200" class="img2">
					</a>
					
						<div class="title">제목 : ${ vo.title } </div>
						<div class="author">작가 : ${ vo.author } </div>
						<div class="genre">장르 : ${ vo.genre } </div>
			
						<p class="rec">추천수 : ${ vo.rec } / 조회수 : ${ vo.views }</p>
					
						<div align="center">
							<input type="button" value="☆" onclick="bookmark(${vo.idx});">
						</div>
		
				</div>
			</c:forEach>
		</div>
		
</body>
</html>