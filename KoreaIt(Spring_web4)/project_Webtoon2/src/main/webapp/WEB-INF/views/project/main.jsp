<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

	<link rel="stylesheet"
		  href="${pageContext.request.contextPath }/resources/css/main.css">
	
	<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap" rel="stylesheet">

<script
   src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
   <script src="${ pageContext.request.contextPath }/resources/js/httpRequest.js"></script>
   
	 <script type="text/javascript">
					
			function login( f ) {
				
				var id = f.id.value;
				var pwd = f.pwd.value;		
				
				if (id == "") {
					alert("아이디를 입력해주세요");
					return;
				}
				if (pwd == "") {
					alert("비밀번호를 입력해주세요");
					return;
				}					
				var url = "login.do";
				var param ="id="+id+"&pwd="+encodeURIComponent(pwd);
				
				sendRequest(url, param, login_resultFn, "GET");
			}
			
			function login_resultFn() {
				if (xhr.readyState == 4 && xhr.status == 200) {
					var data = xhr.responseText;
					
					var json = eval(data);
					
					if (json[0].login_result == 'no') {
						alert("아이디 또는 비밀번호가 일치하지 않습니다");
						return;
					}else {
						alert("로그인 성공");
						
						var id = document.getElementById("id").value;
						location.href='main2.do?id='+id;	//로그인 성공시 원하는 페이지로 매핑 새로 잡아야함
					}
				}
			}
			
			function send(i){
				
				var idx = i;
				
				location.href = "toon_click.do?idx=" + idx;
			}
			
			function logout(){
				
				alert("로그아웃 성공");
				
				location.href = "main.do";
			}
	</script> 
</head>
<body>
<div class="outer_div">

		<img class="logo_img" src="${pageContext.request.contextPath }/resources/img/logo1.png">
		<img class="logo_img2" src="${pageContext.request.contextPath }/resources/img/arin.png">

	<div class="header">
		<ul class="bt">
			<li><a  id="li1" onclick="location.href='search_form.do'">검색하기</a></li>
			<li><a onclick="location.href='boardList.do'">커뮤니티</a></li>
			<li><a onclick="location.href='upload_form.do'">웹툰업로드</a></li>
		</ul>
	</div>
	<!-- 웹툰미리보기(?) -->
			<!-- <div id="webtoon_top">추천</div> -->
	<div class="webtoon">
		
	<c:forEach begin="0" end="7" var="vo" items="${list}">
		<div class="wtimg_box">
			
			<img class="wt_img" src="${pageContext.request.contextPath }/resources/upload/${vo.filename}"
				 onclick="send(${vo.idx});">			                 
			<ul>
				<li>웹툰명:&nbsp ${vo.title }</li>
				<li>만화가:&nbsp ${vo.author }</li>
				<li>장르:&nbsp ${vo.genre }</li>
			</ul>
		</div>
	</c:forEach>
		
	</div>
	
	<!-- 로그인 -->
	<!-- 로그인 성공시 보여지는 화면이 있어야 하므로 if문이 필요하다 -->
	
	<div class="login">
	
	
	
		<!-- 로그인 안할시 -->
		<c:if test="${empty vo.nickname}">
		<form>
			<input id="id" name="id" autocomplete="off" placeholder="아이디">
			<input type="password" name="pwd" placeholder="비밀번호">
			<button type="button" class="login_bt" onclick="login(this.form)">로그인</button>
		</form>
		
		<a id="a0" onclick="location.href='findId.do'">아이디찾기></a>
		<a id="a2" onclick="location.href='signup.do'">회원가입></a>
		</c:if>
		
		<c:if test="${!empty vo.nickname}">
		<div class="login_good">
			<p>${vo.nickname}님 환영합니다.</p>
			<a id="good_a1" onclick="location.href='bookmark_form.do'">북마크></a>
		</div>
			<button type="button" class="good_bt" onclick="logout();"> 로그아웃</button>
		</c:if>
	</div>
	
	<!-- 조회수랭크 -->
	<div class="rank">
		<div class="rank_name">&nbsp&nbsp조회수 순위</div>
		<table>
			<c:forEach begin="0" end="11" var="vo" items="${list1}" varStatus="status">
			<tr>
				<th width=50px align="center">${status.count}.</th>
				<td width=130px onclick="send(${vo.idx})" class="rank1">${vo.title }</td>
				<td>${vo.genre }</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	
	<!-- 커뮤니티 -->
	<div class="community">
	<a class="commu_name">자유게시판</a>
	<div class="commu_header"></div>
		<table border="1" bordercolor="#C7D3D4">
			<tr>
				<th width="70px" height="30px">번호</th>
				<th width="350px" height="30px">제목</th>
				<th width="140px" height="30px">글쓴이</th>
				<th width="150px" height="30px">등록일</th>
				<th width="100px" height="30px">조회</th>
			</tr>
			
			<c:forEach begin="0" end="15" var="vo" items="${list2}">
				<tr>
					<td width="70px" height="30px" align="center">${vo.idx }</td>
					<td width="350px" height="30px">&nbsp&nbsp${vo.subject }</td>
					<td width="140px" height="30px" align="center">${vo.name }</td>
					<td width="150px" height="30px" align="center">${vo.regDate }</td>
					<td width="100px" height="30px" align="center">${vo.readHit }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
</div>
</body>
</html>