package service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import beans.MemberVO;
import repositories.ManagementDAO;
import repositories.ManagementDAOImpl;
import utils.PageMaker;

public class ManagementServiceImpl implements ManagementService {
	
	ManagementDAO dao = new ManagementDAOImpl();

	@Override
	public ArrayList<MemberVO> getMemberList(HttpServletRequest request) {
		// page - 현재 페이지 번호
		// perPageNum - 한번에 보여줄 게시물 개수
		// totalCount - 전체 게시물 개수
		// displayPageNum - 한번에 보여줄 페이지 번호 개수
		int defaultPage = 1;
		String page = request.getParameter("page");
		if(page != null) {
			defaultPage = Integer.parseInt(page);
		}
		PageMaker pm = new PageMaker();
		pm.getCri().setPage(defaultPage);
		pm.setDisplayPageNum(10);
		int totalCount = dao.getMemberTotalCount();
		pm.setTotalCount(totalCount);
		
		request.setAttribute("pm", pm);
		
		ArrayList<MemberVO> memberList
						= dao.getMemberList(pm.getCri());
		return memberList;
	}
}










