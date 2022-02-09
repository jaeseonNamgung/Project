package com.korea.wt;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.jta.UserTransactionAdapter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import dao.UserDAO;
import vo.BoardVO;
import vo.ToonVO;
import vo.UserVO;



@Controller
public class UserController {
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpSession session;
	
	UserDAO user_dao;
	
	public void setUser_dao(UserDAO user_dao) {
		this.user_dao = user_dao;
	}
	
	//로그인전
	@RequestMapping( value= {"/","main.do"} )
	public String main(Model model) {
		
		session.invalidate();
		
		//추천수순위
		List<ToonVO> list = user_dao.Toonlist();
		model.addAttribute("list",list);
		//조회수순위
		List<ToonVO> list1 = user_dao.Views();
		model.addAttribute("list1",list1);
		//게시판 간단히 보여주기
		List<BoardVO> list2 = user_dao.Boardlist();
		model.addAttribute("list2",list2);
		
		return "/WEB-INF/views/project/main.jsp";
	}
	
	//로그인후
	@RequestMapping("main2.do")
	public String main2(Model model,String id) {
		
		//로그인관련
		if(id != null) {
		UserVO vo = user_dao.id_selectOne(id);
		model.addAttribute("vo",vo);
		
		HttpSession session = request.getSession();
		session.setAttribute("vo", vo);
		session.setMaxInactiveInterval(60 * 60);
		
		}
		//추천수순위
		List<ToonVO> list = user_dao.Toonlist();
		model.addAttribute("list",list);
		
		//조회수순위
		List<ToonVO> list1 = user_dao.Views();
		model.addAttribute("list1",list1);
		
		//게시판 간단히 보여주기
		List<BoardVO> list2 = user_dao.Boardlist();
		model.addAttribute("list2",list2);
		
		return "/WEB-INF/views/project/main.jsp";
	}

	@RequestMapping("signup.do")
	public String signup( Model model ) {
		return "/WEB-INF/views/project/signup_page.jsp";
	}
	
	@RequestMapping("/mainpage.do")
	public String login(Model model) {
		return "/WEB-INF/views/project/main_page.jsp";
	}
	
	@RequestMapping("/logoutpage.do")
	public String logout(Model model) {
		return "/WEB-INF/views/project/logout.jsp";
	}
	
	//----------------------- 기능 -------------------------
	
		// 회원가입
			@RequestMapping("/insert.do")
			public String insert(UserVO vo) {
				int res = user_dao.insert1(vo);
				
				return "redirect:main.do";
			}
			
			// 아이디 중복확인
			@RequestMapping("/check_id.do")
			@ResponseBody
			public String id_check(String id) {
				UserVO vo = user_dao.id_selectOne(id);
				
				String id_res = "[{'id_result':'no'}]";
				if (vo == null) {
					id_res = "[{'id_result':'yes'}]";
				}
				
				return id_res;
			}
			
			// 이메일 중복확인
			@RequestMapping("/check_email.do")
			@ResponseBody
			public String email_check(String email) {
				UserVO vo = user_dao.email_selectOne(email);
				
				String email_res = "[{'email_result':'no'}]";
				if (vo == null) {
					email_res = "[{'email_result':'yes'}]";
				}
				
				return email_res;
			}
			
			// 닉네임 중복확인
			@RequestMapping("/check_nickname.do")
			@ResponseBody
			public String nickname_check(String nickname) {
				UserVO vo = user_dao.nickname_selectOne(nickname);
				
				String nickname_res = "[{'nickname_result':'no'}]";
				if (vo == null) {
					nickname_res = "[{'nickname_result':'yes'}]";
				}
				
				return nickname_res;
			}
			
			// 로그인
			@RequestMapping("/login.do")
			@ResponseBody
			public String login(HttpSession session, UserVO vo,Model model) {
				// 로그인이 되면 vo2에 객체가 넘어오게 된다.
				UserVO vo2 = user_dao.login(vo);
				if (vo2 != null) {
					session.setAttribute("loginUser", vo2);
					session.setAttribute("userId", vo2.getId());
				}
				
				String login_res = "[{'login_result':'yes'}]";
				if (vo2 == null) {
					login_res = "[{'login_result':'no'}]";
				}
				
				return login_res;
			}	
			
			//로그아웃
		      @RequestMapping("/logout.do")
		      public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		         try {
		            response.setContentType("text/html; charset=utf-8");
		            String contextPath = request.getContextPath();
		            PrintWriter out = response.getWriter();
		            session.invalidate();
		            out.print("<script>");
		            out.print("alert('로그아웃 되었습니다.');");
		            out.print(String.format("location.href='%s/signup.do';", contextPath));
		            out.print("</script>");
		         } catch (IOException e) {
		            e.printStackTrace();
		         }
		      }
		      
		    //======================================================================================
		      
		    //아이디 찾기 페이지 이동
		  	@RequestMapping("/findId.do")
		  	public String findId(Model model) {
		  		return "/WEB-INF/views/project/findId.jsp";
		  	}
		  	
		  	//------------------------기능------------------------------
		  	
		  	//이메일 검사
		  	@RequestMapping("/findIdAction.do")
		  	@ResponseBody
		  	public String findIdAction(String email) {
		  		String str = "no";
		  		UserVO emailVo = user_dao.email_selectOne(email);
		  		
		  		if(emailVo != null) {
		  			str = "yes";
		  		}
		  		return str;
		  	}
		  	
		  	//아이디 찾기
		  	@RequestMapping("/findIdResult.do")
		  	public String findIdResult(Model model, String email) {
		  		UserVO vo = user_dao.email_selectOne(email);
		  		model.addAttribute("id", vo.getId());
		  		return "/WEB-INF/views/project/findIdResult.jsp";
		  	}
	
	}

