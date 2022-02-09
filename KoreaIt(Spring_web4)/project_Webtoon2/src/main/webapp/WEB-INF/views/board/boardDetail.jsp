<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<!-- jquery 사용을 위한 설정 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/httpRequest.js"></script>
<script>
	
	/* 댓글 쓰기 */
	function send() {
		$.ajax({
			url : 'boardDetailStyleWrite.do',
					/* form으로 넘어온 모든 값을 받는다. */		
			data : $("#commentForm").serialize(),
			type : 'POST',
			success : function(data) {
				if (data == "yes") {
					getCommentList();
					$("#content").val('');
				}
			}
		});

	}
	/* window.load = function()과 같은 기능, 기존에 있던 댓글 정렬 */
	$(function() {
		getCommentList();
	});

	/* 댓글 리스트  */
	function getCommentList() {
		var ref = document.getElementById("ref").value;
		var userId = document.getElementById("userId").value;
		$.ajax({
			url : 'boardDetailStyleAction.do',
			data : 'ref='+ref,
			type : 'POST',
			dataType : 'JSON',
			success : function(data) {

				var html = "";
				var commentCount = data.length;
				// 댓글 인덱스를 역순으로 받기 위한 설정
				var index = data.length;
				if (data.length > 0) {
					for (var i = 0; i < data.length; i++) {
						html += "<table class='commentsTable'>" + "<tr><td><span class='indexSpan1'>"+index+".</span>"
							 + "<span class='indexSpan2'>"+data[i].nickName +"</span></td>";
							if(data[i].del == -1){
								html += "<td><span class ='contentDel'>" + data[i].content+"</span></td>";
							}
							else{
								html+= "<td>" + data[i].content+"</td>";
							}
								
							html += "<td>" + data[i].regDate+"</td>";
							index = index - 1;
					if(data[i].userId == userId&& data[i].del != -1){ /* 사용자가 입력한 댓글만 삭제할 수 있도록 설정 */
						
						html += "</tr><tr><td colspan='2'></td><td class='btnDel'><a href='javascript:sendDel()'>삭제</a></td></tr></table>";
						html += "<input type='hidden' id='commentIdx' name='commentIdx' value='"+data[i].idx+"'/>";
						
						/* 댓글 인덱스를 파라미터로 받기 위한 설정 */
					}
					else{
						html +="</tr></table>";
					}
								
					}
				} else {
					html = "<div class='noComment'><h3>댓글이 없습니다</h3></div>";
				}

				$("#comments").html(html);
				$("#commentCountSpan1").html(commentCount);
				$("#commentCountSpan2").html(commentCount);
			}

		});
	}
	
	
	
	/* 추천 수 */
	function sendRecommend(){
		var userId = document.getElementById("userId").value;
		var idx = document.getElementById("idx").value;
		$.ajax({
			url:'boardRecommend.do',
			data:'userId='+userId+"&idx="+idx,
			dataType:'text',
			type:'post',
			success:function(data){
				if(data == "yes"){
					$("#recommendImage").attr("src", "${pageContext.request.contextPath}/resources/images/recommend.png");
				}		
				else if(data == "no"){
					$("#recommendImage").attr("src", "${pageContext.request.contextPath}/resources/images/recommend1.png");
				}
				else if(data == "noLogin"){
					alert("로그인 후 이용 바랍니다.");
				}
			}
		});
		
	}
	
	$(function(){
		checkRecommend();
	});
	
	/* 상세페이지 들어갔을 때 추천 상태(초기값) */
	function checkRecommend(){
		var userId = document.getElementById("userId").value;
		var idx = document.getElementById("idx").value;
		$.ajax({
			url:"checkRecommend.do",
			data:"userId="+userId+"&idx="+idx,
			type:'post',
			dataType:'text',
			
			success:function(data){
				if(data == "yes"){
					$("#recommendImage").attr("src", "${pageContext.request.contextPath}/resources/images/recommend.png");
				}		
				else if(data == "no"){
					$("#recommendImage").attr("src", "${pageContext.request.contextPath}/resources/images/recommend1.png");
				}
			}
			
		});
	}
	
	/* 댓글 삭제 */
	function sendDel(){
		
		if(!confirm("정말 삭제 하시겠습니까?")){
			return;
		}
		
		var commentIdx = document.getElementById("commentIdx").value; 
		var userId = document.getElementById("userId").value;
		
		$.ajax({
			
			url:'boardCommentDel.do',
			data:'commentIdx='+commentIdx+"&userId="+userId,
			type:'post',
			dataType:'text',
			success:function(data){
				if(data == "yes"){
					alert("삭제되었습니다.");
					getCommentList();
				}else if(data == "no"){
					alert("삭제되지 않았습니다.");
				}
			}
		});
		}
	/* 사용자 글 수정 */
	function sendBoardModify(f){
		var idx = document.getElementById("idx").value;
		var pwd = document.getElementById("pwd").value;
		var checkPwd = prompt("비밀번호를 입력해주세요.");
		if(checkPwd == null){ // 취소 버튼을 눌렀을 때 
			return;
		}
		
		if(pwd != checkPwd){
			alert("비밀번호가 다릅니다. 비밀번호를 다시 확인해주세요");
			return;
		}
		
		var f = document.getElementById("commentForm");
		f.action = "boardModify.do?idx="+idx;
		f.submit();
		
	}
	
	function sendBoardDelete(f){
		var pwd = document.getElementById("pwd").value;
		var idx = document.getElementById("idx").value;
	
		if(!confirm("정말 삭제하시겠습니까?")){
			return;
		}
		var checkPwd = prompt("비밀번호를 입력해주세요.");
		if(checkPwd == null){// 취소 버튼을 눌렀을 때 
			return;
		}
		if(pwd != checkPwd){
			alert("비밀번호가 다릅니다. 비밀번호를 다시 확인해주세요");
			return;
		}
		alert("삭제되었습니다");
		var f = document.getElementById("commentForm");
		f.action = "boardDelete.do?idx="+idx;
		f.submit();
		
	}
</script>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boardDetailStyle.css" />
<meta charset="UTF-8">
<title>상세페이지</title>
</head>
<body>
	<div class="menuTitle">
			<span>Menu</span>
	</div>
	<div class="sideMenu">
		<ul>
			<li><a href="main2.do">메인 페이지</a></li>
			<li><a href="boardList.do">자유 게시판</a></li>
			<li><a href="detailedMenu.do?menu=공지">공지사항</a></li>
		</ul>
	</div>
	<div class="title">
		<h1>어쩔티비</h1>
		<img src="${pageContext.request.contextPath}/resources/images/boardDetailImage.png">
	</div>
	<div class="sideAd">
		<img src="${pageContext.request.contextPath}/resources/images/sideAd.jpg">
		<img src="${pageContext.request.contextPath}/resources/images/side_webtoon02.png">
		<img src="${pageContext.request.contextPath}/resources/images/side_webtoon03.jpg">
	</div>
	<div class="main">
	<form method="POST" id="commentForm">
	<input type="hidden" id = "userId" name="userId" value="${userId}">
	<input type="hidden" id = "idx" name="idx" value="${vo.idx}">
	<input type="hidden" id = "ref"name="ref" value="${vo.ref}">
	<input type="hidden" id="pwd" name="pwd" value="${vo.pwd }">
	<!-- 게시물을 수정하기 위해 pwd를 hidden 속성을 통해 함수로 보낸다 -->
		
		
			<!-- 사용자가 작성한 글만 수정 삭제 가능하도록 설정 -->
			<c:if test="${boardDetailId eq userId }">
			<div class="btnModifyDelete">
				<a href="javascript:sendBoardModify()">수정</a>
				<a href="javascript:sendBoardDelete()">삭제</a>
			</div>
			</c:if>
			<div class="header1">
				<p>${vo.menu}</p>
				<div></div>
				<p>${vo.subject}</p>
				<p>
					<a href="javascript:sendRecommend()"><img id="recommendImage" src="${pageContext.request.contextPath}/resources/images/recommend1.png"/></a>
				</p>
				<p>
					<img src="${pageContext.request.contextPath}/resources/images/eyes.png"><span>${vo.readHit }</span>
				</p>
				<p>
					<img src="${pageContext.request.contextPath}/resources/images/comments.png"><span id="commentCountSpan1"></span>
				</p>
			</div>
			<div class="header2">
				<p>${vo.name }</p>
				<p>
					<fmt:parseDate value="${vo.regDate}" var="regDateFormat" pattern="yyyy-MM-dd HH:mm:ss"/>
					<fmt:formatDate value="${regDateFormat}" pattern="yyyy-MM-dd HH:mm " />
				</p>
			</div>
		
		<div class="content">${vo.content}</div>
		<div class="commentCount"><span>Comments</span><span id="commentCountSpan2"></span></div>
		<div id="comment">
			<c:if test="${loginOk eq 'yesLogin'}">
				<textarea placeholder="댓글을 입력해 주세요." rows="5" cols="30"
					id ="content" name="content"></textarea>
				<a href="javascript:send()">등록</a>
			</c:if>
			<c:if test="${loginOk eq 'noLogin'}">
				<textarea rows="5" cols="30" disabled>로그인 후 이용 바랍니다.</textarea>
			</c:if>
			
		</div>
		<div id="comments"></div>
		</form>
	</div>
</body>
</html>