<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/findId.css">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function sendCheckEmail(f){
		var email = f.email.value.trim();
		$.ajax({
				url:'findIdAction.do',
				data:'email='+email,
				type:'get',
				datatype:'text',
				success: function(data){
					if(data == "no"){
						alert("이메일이 존재하지 않습니다");
						return;
					}else{
						location.href="findIdResult.do?email="+email;
					}
				}
		})
	}	
</script>
</head>
<body img src="${pageContext.request.contextPath}/resources/img/Background.png">
	
	<div class="container">
		
		<div class="signUp_left">
				
			<div class="border">
				
				<div class="image">
					<img src="${pageContext.request.contextPath}/resources/img/logo_signup.png">
				</div>
			</div>
			
		</div>
	
		<div class="signUp_right">
			
			<div class="main">
			<p> ♥ 여러분들의 지루한 일상에<br>웃음을 선물하세요!</p>
				<form>
					<ul>
						<li>
							<input class="email" type="email" name="email" placeholder="이메일을 입력해주세요">
						</li>
						
						<li>
							<input class="yesBtn" type="button" value="이메일 확인" onclick="sendCheckEmail(this.form)">
						</li>
						
						<li>
							<input class="noBtn" type="button" value="돌아가기" onclick="location.href='main.do'">
						</li>
					</ul>
				</form>
			</div>
		</div>
	
	</div>
	
</body>
</html>