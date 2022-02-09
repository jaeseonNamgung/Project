package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vo.BoardVO;
import vo.ToonVO;
import vo.UserVO;


public class UserDAO {
	
	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public List<UserVO> selectList(){
		 
		 List<UserVO> list = sqlSession.selectList("u.user_list"); 
		 return list;
		 }
	
		//한개만 가져오기
//		public String selectOne(String id) {
//			
//			String nickname = sqlSession.selectOne("u.selectOne", id);
//			return nickname;
//		}
		
		// 회원가입
		public int insert1(UserVO vo) {
			int res = sqlSession.insert("u.user_insert", vo);
			
			return res;
		}		

		// 아이디 중복확인
		public UserVO id_selectOne(String id) {
			
			UserVO vo = sqlSession.selectOne("u.id_one", id);
			return vo;
		}
		
		// 이메일 중복확인
		public UserVO email_selectOne(String email) {
			UserVO vo = sqlSession.selectOne("u.email_one", email);
			
			return vo;
		}
		
		// 닉네임 중복확인
		public UserVO nickname_selectOne(String nickname) {
			UserVO vo = sqlSession.selectOne("u.nickname_one", nickname);
			
			return vo;
		}
		
		// 로그인
		public UserVO login(UserVO vo) {
			UserVO vo2 = sqlSession.selectOne("u.login", vo);
				
			return vo2;		
		}
		
		//웹툰목록가져오기 추천수순위용
		public List<ToonVO> Toonlist() {
			List<ToonVO> list = sqlSession.selectList("u.selecttoon");
			
			return list;
		}
		
		//웹툰목록가져오기 조회수순위
		public List<ToonVO> Views(){
			List<ToonVO> list = sqlSession.selectList("u.selecttoon_views");
			
			return list;
		}
		
		public List<BoardVO> Boardlist(){
			List<BoardVO> list = sqlSession.selectList("u.selectboard");
			
			return list;
		}

	

}
