package com.korea.wt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import dao.ProjectDAO;
import util.Common;
import vo.BookmarkVO;
import vo.ToonVO;

@Controller
public class ProjectController {
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ServletContext application;
	
	@Autowired
	HttpSession session;
	
	
	//dao객체 생성
	ProjectDAO project_dao;
	public void setProject_dao(ProjectDAO project_dao) {
		this.project_dao = project_dao;
	}
	
	//만화등록 화면으로 전환
	@RequestMapping("upload_form.do")
	public String upload_form() {
		return Common.Board.VIEW_PATH + "upload_form.jsp";
	}
	
	//만화 등록하기
	@RequestMapping("upload.do")
	public String upload(ToonVO vo) {
		
		int rec = 0;
		int views = 0;
		vo.setRec(rec);
		vo.setViews(views);
		
		//파일 업로드를 위한 절대경로 지정
		String webPath = "/resources/upload/";
		String savePath = application.getRealPath(webPath);
		System.out.println(savePath);		
		
		//파라미터로 넘어온 파일의 정보
		MultipartFile photo = vo.getPhoto();
		
		String filename = "no_file";
		
		//수신된 photo가 존재한다면(비어있지 않다면)
		if(!photo.isEmpty()) {
			filename = photo.getOriginalFilename(); //업로드된 실제 파일명
			
			//파일을 저장할 경로를 생성
			File saveFile = new File(savePath, filename);
			
			if(!saveFile.exists()) {
				saveFile.mkdirs(); //없는경로(upload/파일명)생성
			
			}else {
				//업로드 시도시, 동일한 이름의 파일이 존재할 경우 업로드 시간을 붙여서 중복을 방지
				
				//currentTimeMillis(); : 1970/1/1 ~ 현재까지의 경과시간을 1/1000단위로 저장하고 있는 메서드
				long time = System.currentTimeMillis();
				filename = String.format("%d_%s", time, filename);
				saveFile = new File(savePath, filename);
			}
			
			try {
				//saveFile이 가지고 있는 경로에 실제로 파일을 업로드
				photo.transferTo(saveFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		vo.setFilename(filename);
		
		//vo가 가진 정보를 DB에 insert
		project_dao.insert(vo);
		
		return "redirect:upload_form.do";
	}
	
	//만화검색 화면으로 전환 
	@RequestMapping("search_form.do")
	public String search_form(Model model) {
		return Common.Board.VIEW_PATH + "search_form.jsp";
	}
	
	//만화 검색
	@RequestMapping("search.do")
	public String search(Model model, ToonVO vo) {
		
		List<ToonVO> list = project_dao.selectList(vo);

		model.addAttribute("list", list);
		
		return Common.Board.VIEW_PATH + "search_form.jsp";
	}
	
	//만화 랜덤 검색
	@RequestMapping("random.do")
	public String random(Model model) {
		
		//toon에서 끝 idx
		int toon_max = project_dao.toon_max();
		
		//rnd 생성
		int rnd = 0;
		List<Integer> list = project_dao.idxs();
		
		while(true) {
			
			rnd = new Random().nextInt(toon_max) + 1;
			
			if(list.contains(rnd)) {
				break;
			}
			
		}
		
		//vo 바인딩 + 포워딩
		ToonVO vo = project_dao.selectOne(rnd);
			
		model.addAttribute("vo2", vo );
		return Common.Board.VIEW_PATH + "search_form2.jsp";
	}
	
	//북마크 화면으로 전환 + 북마크 만화 가져오기
	@RequestMapping("bookmark_form.do")
	public String bookmark_form(Model model) {
		
		//user2_id (실제로는 session에서  가져오기)
		String user2_id = (String) session.getAttribute("userId");
		
		//user2_id아이디로  북마크에서 idx 가져오기
		List<Integer> list_idx =  project_dao.bookmark_toon_idx(user2_id);		
		
		for(int i = 0; i < list_idx.size(); i++) {
			
			System.out.println(list_idx.get(i));
			
		}
		
		//bookmark_toon_idx로 가져온 만화들
		List<ToonVO> list = new ArrayList<ToonVO>();
		
		for(int i = 0; i < list_idx.size(); i++) {
			
			ToonVO vo = project_dao.selectOne(list_idx.get(i));
			
			list.add(vo);
		}
		
		model.addAttribute("list", list);
		
		return Common.Board.VIEW_PATH + "bookmark_form.jsp";
	}
	
	//북마크 추가
	@RequestMapping("bookmark.do")
	@ResponseBody //<-- 해당 어노테이션이 적용되어 있는 경우 return 값을 콜백메서드로 보낸다
	public String bookmark(BookmarkVO vo) {
		
		//session
		String user2_id = (String) session.getAttribute("userId");
		System.out.println(user2_id);

		String str = "no";
		//로그인했을 때만 북마크 추가 가능
		if(user2_id != null) {
		
		vo.setUser2_id(user2_id);
		//북마크에 있는 정보 불러와서 중복이면 북마크에 추가 못하게 하기
		List<Integer> list =  project_dao.bookmark_toon_idx(user2_id);
		
		
		if(!list.contains(vo.getToon_idx())) {
			int res = project_dao.insert(vo);	
				if(res == 1) {
					str = "yes";
				}
		}
		
		else {
				
			str = "no";
		}
	}
	
		else {
			str = "logout";
		}
		
		return str;
	}
	
	//북마크 해제
	@RequestMapping("bookmark_delete.do")
	public String bookmark_delete(BookmarkVO vo) {

		//user2_id(실제로는 session에서 받아오기)
		String user2_id = (String) session.getAttribute("userId");
		System.out.println(user2_id);
		vo.setUser2_id(user2_id);
		
		
		project_dao.bookmark_delete(vo);
		
		return "redirect:bookmark_form.do";
	}
	
	//만화  이미지 클릭 시 화면 전환 + 해당 만화 가져오기 + 조회수 증가
	@RequestMapping("toon_click.do")
	public String detail(Model model, int idx) {
		
		//조회수 증가
		project_dao.views(idx);
		
		ToonVO vo = project_dao.selectOne2(idx);
		
		model.addAttribute("vo", vo);
		
		return Common.Board.VIEW_PATH + "toon_click.jsp";
	}
	
	//추천수 +1
	@RequestMapping("rec_plus.do")
	@ResponseBody
	public String rec_plus(int idx) {

		int res = project_dao.rec_plus(idx);
		
		String str = "no";
		
		if(res == 1) {
			str = "yes";
		}
		
		return str;
	}
	
	//추천수 -1
	@RequestMapping("rec_minus.do")
	@ResponseBody
	public String rec_minus(int idx) {
		
		int res = project_dao.rec_minus(idx);
		
		String str = "no";
		
		if(res == 1) {
			str = "yes";
		}
		
		return str;
	}
	
	
}




























