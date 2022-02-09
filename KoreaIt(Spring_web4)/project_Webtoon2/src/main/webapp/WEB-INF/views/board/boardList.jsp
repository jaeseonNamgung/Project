<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
     	
     	/* 공지사항 배경색만 변경하기 위한 코드 */
     	$(function(){
     		var pin = document.getElementById("pinCount").value;
     		/* nth-child(1)은 제목 부분이기 때문에 2부터 시작해야 한다. */
     		var count=2;
     		for(var i = 0; i < pin; i ++){
     			var tr = "tr:nth-child("+count+")";
     			$(tr).css("background","#F2F2F2");
     			count = count + 1;
     		}
     		
     		// 페이징 갯수에 따라 정렬 하기 위해 사용
     		var obj = $('.countBoard ul').position();
     		var n = $('.countBoard ul li').length;
     		var width = $('.countBoard ul').width();
     		$('.countBoard ul').css("left",   obj.left-width+175); 
     		
   
     	});
     	
     	function advancedSearchSend(f){
     		// 상세 메뉴
     		var choice = f.choice.value;
     		// 상세 검색
     		var search = f.search.value.trim();
     		
     		// 내용을 입력하지 않을 경우
     		if(search == ''){
     			alert("내용을 입력해주세요");
     			return;
     		}
     		
     		f.action="advancedSearch.do";
     		f.submit();
     	}
</script>
<head>
<link rel="styleSheet" href="${pageContext.request.contextPath}/resources/css/boardListStyle.css">
<meta charset="UTF-8">
<title>게시판</title>
</head>
<body>
	<div class="divMenu">
		<div class="menuTitle">
			<span>Menu</span>
		</div>
		<div class="sideMenu">
			<ul>
				<li><a href="#" onclick="location.href='main2.do'">메인 페이지</a></li>
				<li><a href="#" onclick="location.href='boardList.do'">자유 게시판</a></li>
				<li><a href="#" onclick="location.href='detailedMenu.do?menu=공지'">공지사항</a></li>
			</ul>
		</div>
	</div>

	<div class="title">
		<h1>어쩔티비</h1>
		<img src="${pageContext.request.contextPath}/resources/images/boardImage.png">
	</div>
	<div class="mainDiv">
	<div class="topSubject" >
		<span>전체게시판</span>
		<!-- 게시물 업로드 -->
		<a href="#" onclick="location.href='boardWrite.do?userId=${userId}'">글 쓰기</a>
	</div>
	<input type="hidden" id="pinCount" name="pinCount" value="${pinCount}"/>
		<table>
			<tr>
				<th>번호</th>
				<th>게시판</th>
				<th class="subject">제목</th>
				<th>글쓴이</th>
				<th>작성일</th>
				<th>조회</th>
				<th>추천</th>
			</tr>
			
			<c:forEach var="l" items="${list}" varStatus="status">
				
				<tr class="endLine">
					<!-- 공지사항 & 일반 게시글 판단 -> 공지사항이면 pin에 값이 -1이고 일반 게시글이면 0으로 설정했기 때문에 -1면 번호에 전체공지로
						나타내고 일반 게시글이면 숫자고 번호를 나타냄 -->
					<c:choose> 
						<c:when test="${l.pin eq -1 }">	
							<td><p>전체공지</p></td>
						</c:when>
						<c:otherwise> 
							<td>${l.idx}</td>
						</c:otherwise>
					</c:choose>
					
					<td><a href="detailedMenu.do?menu=${l.menu}">${l.menu }</a></td>
					<!-- 제목&이미지 : 이미지를 업로드 했을경우 제목 옆에 이미지 아이콘이 보이게 설정-->
					<td class="subject">					<!--boardDetailId: 상세 게시물 id, userId: 사용자 id  -->
						<a href="boardDetail.do?idx=${l.idx}&boardDetailId=${l.userId}&userId=${userId}">${l.subject }</a>
							<c:if test="${l.files ne 'noFile'}">
								<img alt="image_file" src="${pageContext.request.contextPath}/resources/images/image_file.png"
								style="height:15px;">
							</c:if>
							<c:if test="${l.comments ne 0 }">
								<span style="display: inline;">[</span><span>${l.comments}</span><span>]</span>
							</c:if>					
					</td>
					
					
					<!-- 이름 -->
					<td><a href="detailedName.do?name=${l.name}">${l.name }</a></td>
					
					<c:set var="nowDate" value="<%= new java.util.Date() %>"/>
					
					<!-- String 형에서 데이터 형으로 변환 -->
					<fmt:parseDate value="${l.regDate}" var="regDateFormat" pattern="yyyy-MM-dd HH:mm:ss"/>
					<!-- 시간 포맷 -> 오늘 작성된 글은 시간으로 나타나고 하루가 지나면 날짜 년도가 지나면 년도와 날짜로 나타냄 -->
					<c:set var="today"><fmt:formatDate value="${nowDate }" pattern="yy-MM-dd"/></c:set>
					<c:set var="regDate"><fmt:formatDate value="${regDateFormat }" pattern="yy-MM-dd"/></c:set>
					<c:set var="nowYear"><fmt:formatDate value="${nowDate }" pattern="yy-MM"/></c:set>
					<c:set var="regYear"><fmt:formatDate value="${regDateFormat }" pattern="yy-MM"/></c:set>
					
					<c:choose>
						<c:when test="${today eq regDate }">
							<td><fmt:formatDate value="${regDateFormat }" pattern="HH:mm"/></td>
						</c:when>
						<c:when test="${nowYear eq regYear }">
							<td><fmt:formatDate value="${regDateFormat }" pattern="MM.dd"/></td>
						</c:when>
						<c:otherwise>
							<td><fmt:formatDate value="${regDateFormat }" pattern="yy.MM.dd"/></td>
						</c:otherwise>
					</c:choose>
					
					
					<td>${l.readHit }</td>
					<td>${l.recommend }</td>
				</tr>
			</c:forEach>
		</table>
		<div class="countBoard">
			${pageMenu}		
			<form>
		
				<select name="choice" required="required">
					<option value="subject">제목</option>
					<option value="name">글쓴이</option>
				</select>
				<input name="search" placeholder= "검색어를 입력해주세요">
				<button type="button" onclick="advancedSearchSend(this.form)"><img src="${pageContext.request.contextPath}/resources/images/search.png"></button>
			</form>
		</div>
	</div>	
		<div class="sideAd">
		<img src="${pageContext.request.contextPath}/resources/images/sideAd.jpg">
		<img src="${pageContext.request.contextPath}/resources/images/side_webtoon02.png">
		<img src="${pageContext.request.contextPath}/resources/images/side_webtoon03.jpg">
	</div>	
</body>
</html>















