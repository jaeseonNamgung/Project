/* content에 이미지를 업로드 하기 위한 코드 */
$(document).ready(function() {
	/* 에디터 설정 */
	$('#summernote').summernote({
		width : 800, // 에디터 넓이
		height : 300, // 에디터 높이
		minHeight : null, // 최소 높이
		maxHeight : null, // 최대 높이
		focus : true, // 에디터 로딩후 포커스를 맞출지 여부
		lang : "ko-KR", // 한글 설정
		placeholder : '최대 2048자까지 쓸 수 있습니다', //placeholder 설정
			/* callbackk 함수를 이용해 이미지를 controller에 보내주고 여러개의 이미지가 있을 수 있으므로 반복문을 실행*/
			callbacks:{
				onImageUpload: function(files, editor, welEditable) {
		            for (var i = files.length - 1; i >= 0; i--) {
		            	uploadSummernoteImageFile(files[i], this);
		          
		            }
		        }
			} 
				
	});
});

/* Ajax 코드 */
function uploadSummernoteImageFile(file, editor) {
	data = new FormData();
	data.append("file", file);
	$.ajax({
		data : data,
		type : "POST",
		url : "fileUpload.do",
		contentType : false,
		enctype : 'multipart/form-data',
		processData : false,
		success : function(data) {
			
			$(editor).summernote('editor.insertImage', data.url);
			
		}
	});
}

/*게시글 업로드*/
function send(f) {
	
	var menu = f.menu.value;
	var subject = f.subject.value.trim();
	var name = f.name.value.trim();
	var content = f.content.value.trim();
	var pwd = f.pwd.value.trim();
	
	// 유효성 검사
	if(menu == "") {
		alert("게시판을 선택해주세요");
		return;
	}
	if(subject == "") {
		alert("제목을 입력해주세요");
		return;
	}
	if( name == "") {
		alert("이름을 입력해주세요");
		return;
	}
	if(content == "") {
		alert("내용을 입력해주세요");
		return;
	}
	if(pwd == "") {
		alert("비밀번호를 입력해주세요");
		return;
	}
	
	f.action="boardModifyAction.do";
	f.submit();
	
}


/* 취소 버튼을 누를 경우 게시판 페이지로 돌아간다 */
function cancel(f){
	if(!confirm("정말 종료하시겠습니까?")){
		return;
	}
	
	f.action = "boardList.do";
	f.submit();
	
}

function sendBoardList(){
	
	if(!confirm("글 쓰기를 종료하시겠습니까?")) {
		return;
	}	
	window.location.href = "boardList.do";
}
function sendCheck(){
	
	if(!confirm("글 쓰기를 종료하시겠습니까?")) {
		return;
	}	
	window.location.href = "main.do";
}

function sendDetailedMenu() {
	if(!confirm("글 쓰기를 종료하시겠습니까?")) {
		return;
	}
	window.location.href = "detailedMenu.do?menu=공지";
}





