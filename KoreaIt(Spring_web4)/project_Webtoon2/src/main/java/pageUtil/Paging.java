package pageUtil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dao.BoardDAO;
import vo.BoardVO;

@Component
public class Paging {
	
	@Autowired
	private BoardDAO dao;
	
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	
	public String getPaging(int nowPage, List<BoardVO> board, String url, int totalPage) {
		
		StringBuffer sb = new StringBuffer();
		int startPage, endPage;
		startPage = ((nowPage - 1) / Common.Board.BLOCKPAGE) * Common.Board.BLOCKPAGE + 1;
		endPage = startPage + Common.Board.BLOCKPAGE - 1;
		// Math.ceil: 모든 소수점자리를 반올림
		// 만약 totalPage / BLOCKLIST 페이지 연산중 소수점이 있을 경우 나머지 페이지가 있기 때문에 반올림해서(+1) 해서 페이지를 하나 증가 시킨다.
		int realTotalPage = (int)((Math.ceil((double)totalPage/Common.Board.BLOCKLIST)));
		endPage = endPage > realTotalPage ? realTotalPage : endPage;
		
		//------------------------------------------------------------------------------------------------------

		sb.append("<ul class='headerPage'>");
		if(nowPage > 1) {
			sb.append("<li><a href='"+url+"page=");
			sb.append(nowPage-1);
			sb.append("'><</a></li>");
		}
		for(int i = startPage; i <= endPage; i++) {
			if(i == nowPage) {
				sb.append("<li class='page'>");
				sb.append(i);
				sb.append("</li>");
			}else {
				sb.append("<li ><a href='" +url+"page=");
				sb.append(i);
				sb.append("'>");
				sb.append(i);
				sb.append("</a></li>");
			}
		}
		if(nowPage < realTotalPage ) {
			sb.append("<li><a href='"+url+"page=");
			sb.append(nowPage+1);
			sb.append("'>></a></li>");
		}
		sb.append("</ul>");
		return sb.toString();
	}
	
}

