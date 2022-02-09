package boardController;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import dao.BoardDAO;
import dao.UserDAO;
import pageUtil.Common;
import pageUtil.Paging;
import vo.BoardVO;
import vo.CommentVO;
import vo.HeartVO;
import vo.UserVO;

@Controller
public class BoardController {

	@Autowired // Autowired 사용해 외부 주입(BoardDAO를 초기화)
	BoardDAO boardDao;
	@Autowired
	UserDAO userDao;
	@Autowired
	ServletContext sc; 
	@Autowired
	private Paging paging;
	
	public static final String WEB_PATH = "/WEB-INF/views/board/";

	// 전체 게시물 조회
	@RequestMapping(value = {"/boardList.do" })
	public String boardList(Model model, HttpServletRequest request, String page) {
		
		int startList, endList;
		int nowPage;
		String url = "boardList.do?";
		if(page == null) {// 페이지가 null이라면 처음 페이지를 가리키게 설정
			nowPage = 1;
		}else {// 페이지가 null이 아니라면 사용자가 지정한 페이지를 가리키도록 설정
			nowPage = Integer.parseInt(page);
		}		
		endList = nowPage * Common.Board.BLOCKLIST;
		startList = endList - Common.Board.BLOCKLIST + 1;	
		// 전체 페이지 조회
		int totalPage = boardDao.totalPage();
		// 게시글을 가져온다.
		List<BoardVO> list = boardDao.boardList(startList, endList);
		String pageMenu = paging.getPaging(nowPage, list, url, totalPage);
		HttpSession session = request.getSession(); 
		session.removeAttribute("readHit");
		String userId = (String) session.getAttribute("userId");
		int pinCount =0;
		
		// pin(공지사항) 전체 index를 얻기 위한 코드
		for(int i = 0; i < list.size(); i++) {	
			if(list.get(i).getPin() == -1) {
				pinCount = pinCount + 1;
			}
		}
		
		model.addAttribute("pageMenu",pageMenu );
		model.addAttribute("pinCount", pinCount);
		model.addAttribute("page", page);
		model.addAttribute("userId", userId);
		model.addAttribute("list", list);
		return WEB_PATH + "boardList.jsp";
	}

	// menu 글만 조회
	@RequestMapping("/detailedMenu.do")
	public String detailedMenu(Model model, String menu, String page) {
		int startList, endList;
		int nowPage;
		String url = "detailedMenu.do?menu="+menu+"&";
		if(page == null) {// 페이지가 null이라면 처음 페이지를 가리키게 설정
			nowPage = 1;
		}else {// 페이지가 null이 아니라면 사용자가 지정한 페이지를 가리키도록 설정
			nowPage = Integer.parseInt(page);
		}
		
		// menu 글의 전체 글 수를 조회
		int totalPage = boardDao.totalMenuPage(menu);
		endList = nowPage * Common.Board.BLOCKLIST;
		startList = endList - Common.Board.BLOCKLIST + 1;
		
		List<BoardVO> list = boardDao.detailedMenu(startList, endList, menu);
		String pageMenu = paging.getPaging(nowPage, list, url,totalPage);
		model.addAttribute("list", list);
		model.addAttribute("pageMenu",pageMenu );
		return WEB_PATH + "boardList.jsp";
	}

	// name 글만 조회
	@RequestMapping("/detailedName.do")
	public String detailedName(Model model, String name, String page) {
		
		int startList, endList;
		int nowPage;
		String url = "detailedName.do?name="+name+"&";
		if(page == null) {// 페이지가 null이라면 처음 페이지를 가리키게 설정
			nowPage = 1;
		}else {// 페이지가 null이 아니라면 사용자가 지정한 페이지를 가리키도록 설정
			nowPage = Integer.parseInt(page);
		}
		
		// name 글의 전체 글 수를 조회
		int totalPage = boardDao.totalNamePage(name);
		endList = nowPage * Common.Board.BLOCKLIST;
		startList = endList - Common.Board.BLOCKLIST + 1;
		
		List<BoardVO> list = boardDao.detailedName(startList, endList, name);

		String pageMenu = paging.getPaging(nowPage, list, url,totalPage);
		model.addAttribute("list", list);
		model.addAttribute("pageMenu",pageMenu );
		return WEB_PATH + "boardList.jsp";
	}
	
	// 상세 검색
	@RequestMapping("/advancedSearch.do")
	public String advancedSearch(Model model, String choice, String search, String page) {
		int startList, endList;
		int nowPage;
		String url = "advancedSearch.do?choice="+choice+"&search="+search+"&";
		if(page == null) {// 페이지가 null이라면 처음 페이지를 가리키게 설정
			nowPage = 1;
		}else {// 페이지가 null이 아니라면 사용자가 지정한 페이지를 가리키도록 설정
			nowPage = Integer.parseInt(page);
		}	
		// name 글의 전체 글 수를 조회
		int totalPage = boardDao.totalAdvancedSearchPage(choice,search);
		endList = nowPage * Common.Board.BLOCKLIST;
		startList = endList - Common.Board.BLOCKLIST + 1;		
		List<BoardVO> list = boardDao.advancedSearch(startList, endList, choice, search);
		String pageMenu = paging.getPaging(nowPage, list, url,totalPage);
		model.addAttribute("list", list);
		model.addAttribute("pageMenu",pageMenu );
		return WEB_PATH + "boardList.jsp";
	}

	// 글 쓰기 페이지로 이동
	@RequestMapping("/boardWrite.do")
	public String boardWrite(Model model, String userId) {
		model.addAttribute("userId", userId);
		return WEB_PATH + "boardWrite.jsp";
	}

	// 파일 업로드
	@RequestMapping(value = "/fileUpload.do", produces = "application/json; charset=utf8")
	@ResponseBody
	public String fileUpload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request,
			HttpServletResponse response) {
		// 호출한 ajax에 리턴값은 json으로 반환
		JsonObject jsonObject = new JsonObject();

		String webPath = "/resources/upload/";
		String realPath = sc.getRealPath(webPath);

		String fileName = "noFile";

		if (!multipartFile.isEmpty()) {
			fileName = multipartFile.getOriginalFilename();

			File file = new File(realPath, fileName);

			if (!file.exists()) {
				file.mkdirs();
			} else {
				// 중복된 이름을 가진 이미지가 있을겨우 고유의 값을 가디도록 UUID 클래스를 사용
				fileName = UUID.randomUUID() + fileName;
				file = new File(realPath + fileName);
			}

			try {
				multipartFile.transferTo(file);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			jsonObject.addProperty("url", "resources/upload/" + fileName);
			jsonObject.addProperty("responseCode", "success");
		}

		String a = jsonObject.toString();
		return a;
	}

	// 글 쓰기 업로드
	@RequestMapping("/boardWriteAction.do")
	public String boardWrite(BoardVO vo) {

		// files가 공백이면(즉 업로드하지 않았으면) nofile로 값을 넣는다.
		if (vo.getFiles().isEmpty())
			vo.setFiles("noFile");
		if("공지사항".equals(vo.getMenu())) {
			boardDao.notice(vo); 
			// 공지사항일 경우 pin을 -1 로 설정
		}else {
			boardDao.boardWrite(vo);
		}
		
		return "redirect:boardList.do";

	}

	// 상세페이지 이동
	@RequestMapping("/boardDetail.do")
	public String boardDetail(HttpServletRequest request, Model model, int idx, String boardDetailId,String userId) {

		// 상세 페이지로 이동하기 위해 인덱스로 데이터 베이스에서 정보가져오기
		BoardVO vo = boardDao.boardDetail(idx);

		HttpSession session = request.getSession();

		// 로그인 상태 검사
		String loginOk = "noLogin";
		if (userId.equals(session.getAttribute("userId"))) {
			loginOk = "yesLogin";
		}
		
		// 무한 조회수 방지
		if((String)session.getAttribute("readHit") == null) {
			session.setAttribute("readHit", "yes");
			boardDao.boardReadHit(idx);
		}

		model.addAttribute("loginOk", loginOk);
		model.addAttribute("boardDetailId", boardDetailId);
		model.addAttribute("userId", userId);
		model.addAttribute("vo", vo);
		return WEB_PATH + "boardDetail.jsp";

	}

	// 댓글 입력 Ajax
	@RequestMapping("/boardDetailStyleWrite.do")
	@ResponseBody
	public String boardDetailStyleAction(CommentVO vo) {
		// 회원 닉네임 추가 코드(회원 정보에서 받아와야 한다.)
		UserVO userVO = userDao.id_selectOne(vo.getUserId());
		// 닉네임에 첫 글자만 나오고 나머지는 '*'로 표시
		String str = userVO.getName().charAt(0)+"***";
		vo.setNickName(str);
		vo.setDel(0);
		
		// board 테이블에 댓글 수를 추가(게시판에 댓글 수를 보이게 하기 위해서)
		boardDao.boardCommentCount(vo.getRef());
		
		boardDao.boardDetailStyleAction(vo);
		return "yes";
	}

	// 댓글 리스트 Ajax 
	@RequestMapping("/boardDetailStyleAction.do")
	@ResponseBody
	public List<CommentVO> boardDetailStyleAction(int ref) {
		List<CommentVO> list = boardDao.commentSelect(ref);	
		return list; 
	}

	@RequestMapping("/boardRecommend.do")
	@ResponseBody 
	public String boardRecommand(String userId, int idx, HttpServletRequest request) {

		String str = "no";
		HttpSession session = request.getSession();
		HeartVO heartVo = new HeartVO();
		BoardVO boardVo = boardDao.boardDetail(idx);
		heartVo.setIdx(idx);
		heartVo.setUserId(userId);
		// 로그인 체크
		if (userId.equals(session.getAttribute("userId"))) {
			int heartCheck = boardDao.boardHeart(heartVo);
			// 만약 list가 비어 있다면 처음 좋아요를 누른 사용자이기 때문에 데이터베이스에 insert한다.
			
			if(heartCheck== -1) { // -1이라면 값이 없고 처음 좋아요를 누른걸로 판단
				boardDao.heartInsert(heartVo); // 좋아요 테이블에 값 삽입
				heartVo.setBoardNumber(1); // boardNumber에 1로 변경
				boardDao.heartNumberModify(heartVo); // 테이블 수정
				boardVo.setRecommend(boardVo.getRecommend() + 1);
				// board 테이블에 recommend에 값을 +1한다(조회수 up)
				boardDao.boardRecommendCount(boardVo);
				// 테이블에 recommend를 +1로 변경
				str = "yes";
			}
			else if (heartCheck == 0) {// 좋아요가 눌러져 있지 않을 때
	
				heartVo.setBoardNumber(1);
				// 좋아요를 취소하는 동작이기 때문에 boardNumber를 0으로 변경
				boardDao.heartNumberModify(heartVo);
				// 테이블에 boardNumber 값을 수정
				boardVo.setRecommend(boardVo.getRecommend() + 1);
				//recommend(조회수) 1 감소
				boardDao.boardRecommendCount(boardVo);
				// 테이블에 recommend 수정
				str = "yes";
			} else if(heartCheck == 1) { // 좋아요가 눌러져 있을 경우 
					
					heartVo.setBoardNumber(0);
					// 좋아요를 누른 동작이기 때문에 boardNumber를 1로 변경
					boardDao.heartNumberModify(heartVo);
					boardVo.setRecommend(boardVo.getRecommend() - 1);
					boardDao.boardRecommendCount(boardVo);
					//if index ==1 동작과 동일
					str = "no";
			}
		}
		else { // 로그인 되어 있지 않았을 때
			str = "noLogin";
		}
		return str;
	}
	
	@RequestMapping("/checkRecommend.do")
	@ResponseBody
	public String checkRecommend(HttpServletRequest request ,String userId, int idx) {
		HttpSession session = request.getSession();
		String str = "no";
		HeartVO heartVo = new HeartVO();
		heartVo.setIdx(idx);
		heartVo.setUserId(userId);
		if(userId.equals(session.getAttribute("userId"))) {
			int heartCheck = boardDao.boardHeart(heartVo);
			if(heartCheck==-1 || heartCheck == 0) { // 좋아요를 누르지 않았을 경우
				str="no";
			}else {
				str="yes"; // 좋아요가 눌러져 있을 경우
			}
		}
		
		return str;
}
	
	// 댓글 삭제
	@RequestMapping("/boardCommentDel.do")
	@ResponseBody
	public String boardCommentDel(int commentIdx, String userId) {
		
		CommentVO vo = new CommentVO();
		vo.setIdx(commentIdx);
		vo.setUserId(userId);
		
		//삭제할 정보를 받기위한 객체
		CommentVO deleteVO = new CommentVO();
		// 삭제할 정보를 받아온다.
		deleteVO = boardDao.boardCommentDel(vo);
		
		deleteVO.setContent("삭제된 글입니다.");
		deleteVO.setDel(-1);
		
		// 삭제된 댓글 다시 수정
		int res = boardDao.boardCommentUpdate(deleteVO);
		String str = "no";
		if(res == 1) {
			str = "yes";
		}else {
			str = "no";
		}
		return str;
	}
	
	// 게시물 수정
	@RequestMapping("/boardModify.do")
	public String boardModify(Model model, int idx) {
		// 기존에 만들어 놓은 boardDetail 사용
		BoardVO vo = boardDao.boardDetail(idx);
		model.addAttribute("vo", vo);
		return WEB_PATH + "boardModifyAction.jsp";
	}
	
	// 데이터 베이스에 게시물 수정 저장
	@RequestMapping("/boardModifyAction.do")
	public String boardModifyAction(BoardVO vo) {
		// 게시물 수정 데이터베이스에 저장
		boardDao.boardModify(vo);
		
		return "redirect:boardList.do";
	}
	
	// 게시물 삭제
	@RequestMapping("/boardDelete.do")
	public String boardDelete(int idx) {
		// 데이터베이스에 게시물 삭제 
		boardDao.boardDelete(idx);
		return "redirect:boardList.do";
	}
}





