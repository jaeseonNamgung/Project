<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet" 
		  href="${pageContext.request.contextPath }/resources/css/signup.css">
	<script src="${ pageContext.request.contextPath }/resources/js/httpRequest.js"></script>
	
	<script>
	
		// 아이디, 닉네임, 이메일 중복체크를 했는지를 확인하는 변수
		 	var b_idCheck = false;
			var b_emailCheck = false;
			var b_nicknameCheck = false;
		
		// 회원가입
			function signUp(f){
			var id= f.id.value.trim();
			var pwd= f.pwd.value.trim();
			var name= f.name.value.trim();
			var email=f.email.value.trim();
			var nickname= f.nickname.value.trim();
			
			if(id == ""){
				alert("아이디를 입력해주세요");
				return;
			}
			if (!b_idCheck) {
				alert("아이디 중복확인을 해주세요");
				return;
			}
			
			if(pwd == ""){
				alert("비밀번호를 입력해주세요");
				return;
			}
			
			if (name == "") {
				alert("이름을 입력해주세요");
				return;
			}
			if(email == ""){
				alert("이메일을 입력해주세요");
				return;
			}
			var email_pattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
			if (!email_pattern.test(email)) {
				alert("이메일을 올바른 형식으로 입력해주세요");
				return;
			}
			if (!b_emailCheck) {
				alert("이메일 중복확인을 해주세요");
				return;
			}	 
			if(nickname == ""){
				alert("닉네임을 입력해주세요");
				return;
			}
			 if (!b_nicknameCheck) {
				alert("닉네임 중복확인을 해주세요");
				return;
			} 
			
			f.action = "insert.do";
			f.method = "post";
			f.submit();
		}
		
		// 아이디 중복확인 -------------------------------------------
 			function check_id() {
				var id = document.getElementById("id").value;
				
				if (id == "") {
					alert("아이디를 입력해주세요");
					return;
				}
				
				var url = "check_id.do";
				var param = "id=" + id;
				
				sendRequest(url, param, id_resultFn, "GET");
			}
			
			function id_resultFn() {
				if (xhr.readyState == 4 && xhr.status == 200) {
					var data = xhr.responseText;
					
					var json = eval(data);
					
					if (json[0].id_result == 'no') {
						alert("이미 사용중인 아이디입니다");
						return;
					}else{
						alert("사용가능한 아이디입니다");
						b_idCheck = true;
						
						document.getElementById("id").readOnly = true;
					}
				}
			} 
			
		// 이메일 중복확인 -------------------------------------------
 			function check_email() {
				var email = document.getElementById("email").value;
				
				if (email == "") {
					alert("이메일을 입력해주세요");
					return;
				}
				
				var url = "check_email.do";
				var param = "email=" + email;
				
				sendRequest(url, param, email_resultFn, "GET");
			}
			
			function email_resultFn() {
				if (xhr.readyState == 4 && xhr.status == 200) {
					var data = xhr.responseText;
					
					var json = eval(data);
					
					if (json[0].email_result == 'no') {
						alert("이미 사용중인 이메일입니다");
						return;
					}else {
						alert("사용가능한 이메일입니다");
						b_emailCheck = true;
						
						document.getElementById("email").readOnly = true;
					}
				}
			}
			
		// 닉네임 중복확인 -------------------------------------------
 			function check_nickname() {
				var nickname = document.getElementById("nickname").value;
				
				if (nickname == "") {
					alert("닉네임을 입력해주세요");
					return;
				}
				
				var url = "check_nickname.do";
				var param = "nickname=" + nickname;
				
				sendRequest(url, param, nickname_resultFn, "GET");
			}
			
			function nickname_resultFn() {
				if (xhr.readyState == 4 && xhr.status == 200) {
					var data = xhr.responseText;
					
					var json = eval(data);
					
					if (json[0].nickname_result == 'no') {
						alert("이미 사용중인 닉네임입니다");
						return;
					}else {
						alert("사용가능한 닉네임입니다");
						b_nicknameCheck = true;
						
						document.getElementById("nickname").readOnly = true;
					}
				}
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
	            <p>  ♥ 여러분들의 지루한 일상에<br>웃음을 선물하세요!</p>
	                <form>                    
	                    <ul>
	                        <li>
	                        <input type="text" name="name" autocomplete="off" placeholder="이름" autofocus class="name_text">
	                        </li>
	                        
	                        <li>
	                        <input type="text" name="id" autocomplete="off" placeholder="아이디" class="id_text" id="id">
	                        <input type="button" value="중복확인" onclick="check_id();" class="id_check">
	                        </li>
	                        
	                        <li>
	                        <input type="password" name="pwd" autocomplete="off" placeholder="비밀번호" class="pwd_text">
	                        </li>
	                        
	                        <li>
	                        <input type="email" name="email" autocomplete="off" placeholder="ex) abc@abc.com" class="email_text" id="email">
	                        <input type="button" value="중복확인" onclick="check_email()" class="email_check">
	                        </li>
	                        
	                        <li>
	                        <input type="text" name="nickname" autocomplete="off" placeholder="닉네임" class="nick_text" id="nickname"> <!-- style="margin-bottom: 40px;" -->
	                        <!-- <input type="button" value="중복확인" onclick="check_nickname();" class="nickname_check"> -->
	                        <input type="button" value="중복확인" onclick="check_nickname()" class="nickname_check">
	                        </li>
	                        
	                        <li>
	                        <input type="button" value="회원가입" onclick="signUp(this.form)" class="btn_signUp" style="width: 268px; padding: 15px 0;">
	                        </li>                       
	                    </ul>                
	                </form>                
	            </div>
	        </div>

    	</div>
</body>
</html>