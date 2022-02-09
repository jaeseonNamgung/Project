package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.BookmarkVO;
import vo.ToonVO;

public class ProjectDAO {
	
	SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//만화 등록
	public int insert(ToonVO vo) {
		
		int res = sqlSession.insert("p.project_insert", vo);
		return res;
	}

	//만화 가져오기
	public List<ToonVO> selectList(ToonVO vo){
		
		String num = "1";
		
		if(vo.getOrder().equals("추천순")) {
			System.out.println(vo.getOrder());
			num = "2";
		}
		
		if(vo.getOrder().equals("조회순")) {
			System.out.println(vo.getOrder());
			num = "3";
		}
		
		
		List<ToonVO> list = null;
		
		//제목
		if(!vo.getTitle().isEmpty() && vo.getAuthor().isEmpty() && vo.getGenre().equals("전체")) {
			
			list = sqlSession.selectList("p2.project_list_title" + num, vo);
		}
		
		//작가
		if(vo.getTitle().isEmpty() && !vo.getAuthor().isEmpty() && vo.getGenre().equals("전체")) {
			
			list = sqlSession.selectList("p2.project_list_author" + num, vo);
		}
		
		//장르
		if(vo.getTitle().isEmpty() && vo.getAuthor().isEmpty() && !vo.getGenre().equals("전체")) {
			
			list = sqlSession.selectList("p2.project_list_genre" + num, vo);
		}
		
		//제목 + 작가
		if(!vo.getTitle().isEmpty() && !vo.getAuthor().isEmpty() && vo.getGenre().equals("전체")) {
			
			list = sqlSession.selectList("p2.project_list_title_author" + num, vo);
		}
		
		//제목 + 장르
		if(!vo.getTitle().isEmpty() && vo.getAuthor().isEmpty() && !vo.getGenre().equals("전체")) {
			
			list = sqlSession.selectList("p2.project_list_title_genre" + num, vo);
		}
		
		//작가 + 장르
		if(vo.getTitle().isEmpty() && !vo.getAuthor().isEmpty() && !vo.getGenre().equals("전체")) {
			
			list = sqlSession.selectList("p2.project_list_author_genre" + num, vo);
		}
		
		//제목 + 작가 + 장르
		if(!vo.getTitle().isEmpty() && !vo.getAuthor().isEmpty() && !vo.getGenre().equals("전체")) {
			
			list = sqlSession.selectList("p2.project_list_all" + num, vo);
		}
		//전체 가져오기
		if(vo.getTitle().isEmpty() && vo.getAuthor().isEmpty() && vo.getGenre().equals("전체")) {
			
			list = sqlSession.selectList("p2.project_list" + num, vo);
		}
		
		
		return list;
	}
	
	//끝 idx
	public int toon_max() {
		
		int toon_max = sqlSession.selectOne("p.toon_max");
		
		return toon_max;
	}
	
	//만화 랜덤으로 가져오기
	public ToonVO selectOne(int rnd){
		
		ToonVO vo = sqlSession.selectOne("p.random", rnd);
		
		return vo;
		
	}
	
	//만화 idx들 가져오기
	public List<Integer> idxs(){
		
		List<Integer> list = sqlSession.selectList("p.idxs");
		
		return list;
	}
	
	//북마크 추가
	public int insert(BookmarkVO vo) {
		
		int res = sqlSession.insert("p.bookmark_insert", vo);
		
		return res;
	}
	
	//북마크에서 해당 만화 삭제
	public int bookmark_delete(BookmarkVO vo) {
		
		int res = sqlSession.delete("p.bookmark_delete", vo);
		
		return res;
	}
	
	//이미지 클릭 시 만화 가져오기
	public ToonVO selectOne2(int idx) {
		
		ToonVO vo = sqlSession.selectOne("p.toon_click", idx);
		
		return vo;
	}
	
	//추천수 +1
	public int rec_plus(int idx) {
		
		int res = sqlSession.update("p.rec_plus", idx);
		
		return res;
	}
	
	//추천수 -1
	public int rec_minus(int idx) {
		
		int res = sqlSession.update("p.rec_minus", idx);
		
		return res;
	}
	
	//조회수 +1
	public int views(int idx) {
		
		int res = sqlSession.update("p.views", idx);
		
		return res;
	}
	
	//북마크에서 idx 가져오기
	public List<Integer> bookmark_toon_idx(String user2_id) {
		
		List<Integer> list = sqlSession.selectList("p.bookmark_toon_idx", user2_id);
		
		return list;
	}
	
}












