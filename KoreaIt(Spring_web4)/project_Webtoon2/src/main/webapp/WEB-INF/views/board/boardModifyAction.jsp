<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- include libraries(jQuery, bootstrap) -->
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<!-- include summernote css/js-->
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/boardModifyActionScript.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/httpRequest.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boardWriteStyle.css"/>
<meta charset="UTF-8">
<meta charset="UTF-8">
<title>게시물 수정</title>
</head>
<body>
<div class="divMenu">
	<div class="menuTitle">
			<span>Menu</span>
	</div>
		<div class="sideMenu">
			<ul>
				<li><a href="#" onclick="sendCheck()">메인 페이지</a></li>
				<li><a href="#" onclick="sendBoardList()">자유 게시판</a></li>
				<li><a href="#" onclick="sendDetailedMenu()">공지사항</a></li>
			</ul>
		</div>
	</div>
	<div class="sideAd">
		<img src="${pageContext.request.contextPath}/resources/images/sideAd.jpg">
		<img src="${pageContext.request.contextPath}/resources/images/side_webtoon02.png">
		<img src="${pageContext.request.contextPath}/resources/images/side_webtoon03.jpg">
	</div>
<form  method="POST" >
		
		<div class="main">
			<div class="mainDiv">
				<h1>게시글 수정</h1>
				<img src="${pageContext.request.contextPath}/resources/images/baordWriteImage.png">
			</div>
		<div class="menu">
			<label for="menu">게시판</label>
			<select id="menu" name="menu" required="required">
				<option value="" selected>게시물</option>
				<option value="판타지">판타지</option>
				<option value="로맨스">로맨스</option>
				<option value="스포츠">스포츠</option>
				<option value="개그">개그</option>
				<option value="스릴러">스릴러</option>
				<option value="드라마">드라마</option>
				<option value="액션">액션</option>
			</select>
		</div>
		
		<div class="name">
			<label for="name">작성자</label>
			<input name="name" id="name" placeholder="작성자" value=${vo.name }>
		</div>
		
		<div class="subject">
			<label for="subject">제목</label>
			<input name="subject" id="subject" placeholder="제목" value=${vo.subject }>
		</div>
		<div class="content">
			<textarea name="content" id="summernote" >${vo.content }</textarea>
		</div>
		<div class="pwd">
			<label for="pwd">비밀번호</label>
			<input type="password" id="pwd" name="pwd" value=${vo.pwd }>
			<p id="pwdCheck"></p> 
		</div>
		<div class="button">
			<input type="hidden" name="idx" value="${vo.idx }">
			<input type="button" value="업로드" onclick="send(this.form)">
			<input type="button" value="돌아가기" onclick="cancel(this.form)">
		</div>
		</div>
	</form>
</body>
</html>