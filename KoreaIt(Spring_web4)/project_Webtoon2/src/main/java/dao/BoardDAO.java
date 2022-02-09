package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vo.BoardVO;
import vo.CommentVO;
import vo.HeartVO;

@Component
public class BoardDAO {
	SqlSession sqlSession;
	
	@Autowired
	public BoardDAO(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 전체 글 조회
	public List<BoardVO> boardList(int startList, int endList){
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startList", startList);
		map.put("endList", endList);
		return sqlSession.selectList("b.boardList", map);
	}
	
	// menu 글만 조회
	public List<BoardVO> detailedMenu(int startList, int endList, String menu){
		// 자료형이 서로 다르기 때문에 문자열로 통일해서 map에 담는다.
		Map<String, String> map = new HashMap<String, String>();
		String strStartList = Integer.toString(startList);
		String strEndList = Integer.toString(endList);
		
		map.put("startList", strStartList);
		map.put("endList", strEndList);
		map.put("menu", menu);
		return sqlSession.selectList("b.detailedMenu", map);
	}
	// menu 전체 페이지 조회
	public int totalMenuPage(String menu) {
		return sqlSession.selectOne("b.totalMenuPage", menu);
	}
	// name 글만 조회
	public List<BoardVO> detailedName(int startList, int endList,String name){
		// 자료형이 서로 다르기 때문에 문자열로 통일해서 map에 담는다.
		Map<String, String> map = new HashMap<String, String>();
		String strStartList = Integer.toString(startList);
		String strEndList = Integer.toString(endList);
		
		map.put("startList", strStartList);
		map.put("endList", strEndList);
		map.put("name", name);
		return sqlSession.selectList("b.detailedName", map);
	}
	// name 전체 페이지 조회
	public int totalNamePage(String name) {
		return sqlSession.selectOne("b.totalNamePage", name);
	}
	// 상세 검색
	public List<BoardVO> advancedSearch(int startList, int endList,String choice, String search){
		Map<String, String> map = new HashMap<String, String>();
		map.put("choice", choice);
		map.put("search", search);
		String strStartList = Integer.toString(startList);
		String strEndList = Integer.toString(endList);
		
		map.put("startList", strStartList);
		map.put("endList", strEndList);
		return sqlSession.selectList("b.advancedSearch", map);
	}
	// 상세 검색 전체 페이지 수 조회
	public int totalAdvancedSearchPage(String choice, String search) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("choice", choice);
		map.put("search", search);
		return sqlSession.selectOne("b.totalAdvancedSearchPage", map);
	}

	// 전체 게시글 갯수를 조회
	public int totalPage() {
		return sqlSession.selectOne("b.totalPage");
	}
	
	// 글 쓰기
	public void boardWrite(BoardVO vo) {
		sqlSession.insert("b.boardWrite", vo);
	}
	// 공지사항 등록
	public void notice(BoardVO vo) {
		sqlSession.insert("b.notice", vo);
	}
	
	// 상세 페이지 조회
	public BoardVO boardDetail(int idx) {
		return sqlSession.selectOne("b.boardDetail", idx);
	}

	
	// 조회수 
	public void boardReadHit(int idx) {
		sqlSession.update("b.boardReadHit", idx);
	}
	
	// board 테이블에 댓글 수 추가
	public void boardCommentCount(int idx) {
		sqlSession.update("b.boardCommentCount", idx);
	}
	
	 public void boardDetailStyleAction(CommentVO vo) {
	  sqlSession.insert("b.boardDetailStyleAction", vo); 
	  }
	 
	
	public List<CommentVO> commentSelect(int ref){
		return sqlSession.selectList("b.commentSelect", ref);
	}
	
	// id로 페이지 조회 후 데이터 베이스에 저장된 값을 받아온다.
		public int boardHeart(HeartVO vo) {
			return sqlSession.selectOne("b.boardHeart", vo);
		}
			
	// heart 테이블 insert
		public void heartInsert(HeartVO vo) {
			
			sqlSession.insert("b.heartInsert", vo);
		}

		
		// recommend 설정
		public void heartNumberModify(HeartVO vo) {
				
			sqlSession.update("b.heartNumberModify", vo);
		}
		
		// 좋아요을 눌렀을 경우 그 게시물을 1을 추가 취소했을 때는 1을 감소
		public void boardRecommendCount(BoardVO vo) {
			sqlSession.update("b.boardRecommendCount", vo);
		}
		
		// 댓글 삭제를 위해 저장된 인덱스의 정보를 가져온다. 
		public CommentVO boardCommentDel(CommentVO vo) {
			return sqlSession.selectOne("b.boardCommentDel", vo);
		}
		
		// 삭제된 댓글을 다시 수정
		public int boardCommentUpdate(CommentVO vo) {
			return sqlSession.update("b.boardCommentUpdate", vo);
		}
		
		// 게시물 수정
		public void boardModify(BoardVO vo) {
			sqlSession.update("b.boardModify", vo);
		}
		
		
		// 게시물 삭제
		public void boardDelete(int idx) {
			sqlSession.delete("b.boardDelete", idx);
		}

}
