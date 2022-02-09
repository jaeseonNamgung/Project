<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

	<link rel="stylesheet" 
	href="${ pageContext.request.contextPath }/resources/css/search.css">
	
	<script>
	
		function send(f){
			
			var photo = f.photo.value;
			var title = f.title.value.trim();
			var author = f.author.value.trim();
			var genre = f.genre.value.trim();
			var website = f.website.value;
			
			//유효성 체크
			
			if(photo == ''){
				alert("이미지를 추가하세요");
				return;
			}
			
			if(title == ''){
				alert("제목을 입력하세요");
				return;
			}
			
			if(author == ''){
				alert("작가명을 입력하세요");
				return;
			}
			
			if(genre == ''){
				alert("장르를 선택하세요");
				return;
			}
			
			if(website == ''){
				alert("사이트를 등록하세요");
				return;
			}
			
			f.action = "upload.do";
			f.submit();
			
			alert("웹툰 등록 완료!!")
		}
		
		function genre_put(g2){
			
			var genre = document.getElementById("g");
			g.value = g2;
			
		}
		
	</script>
</head>
<body>
	<form method="post" enctype="multipart/form-data">
			
			<img class="logo_img2" src="${pageContext.request.contextPath }/resources/img/arin.png">	
			<div id="title5">웹툰 업로드</div>
			
			<div id="search2">
				<div>
					<p class="imgs">이미지 : </p>
					<input type="file" name="photo" class="img3">
				</div>
				
				<div>
					<p class="titles">제목 : </p>
					<input name="title" class="title3">
				</div>
				
				<div>
					<p class="authors">작가 : </p>
					<input name="author" class="author3">
				</div>
				
				<div>
					<p class="genres">장르 : </p>
					<input name="genre" type="text" value="" id="g" class="genre3" readonly>
	
					<div class="buttons">
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
					<p class="websites">사이트 : </p>
					<input name="website" class="website3">
				</div>
				
				
				<div class="buttons2">
					<input type="button" value="등록하기" onclick="send(this.form);">
					<input type="button" value="취소하기" onclick="location.href='main2.do'">
				</div>
			</div>
	</form>
</body>
</html>