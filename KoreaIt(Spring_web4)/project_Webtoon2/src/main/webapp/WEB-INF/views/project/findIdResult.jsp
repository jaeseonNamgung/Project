<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/findIdResult.css">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body img
	src="${pageContext.request.contextPath}/resources/img/Background.png">


	<div class="container">
	
		<div class="signUp_left">
			<div class="border">
				<div class="image">
					<img
						src="${pageContext.request.contextPath}/resources/img/logo_signup.png">
				</div>
			</div>
		</div>
		
		<div class="signUp_right">
		
			<div class="main">
				<p>	 ♥ 여러분들의 지루한 일상에<br>웃음을 선물하세요!</p>
				<p class="idP">
					<span>아이디는</span><span class="idSpan">${id}</span><span>입니다.</span>
				</p>
				
				<input type="button" value="로그인 페이지로 돌아가기"
				onclick="location.href='main.do'">
			</div>
			
		</div>
		
	</div>
</body>
</html>