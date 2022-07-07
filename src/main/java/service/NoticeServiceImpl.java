package service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import beans.MemberVO;
import beans.NoticeVO;
import repositories.NoticeDAO;
import repositories.NoticeDAOImpl;
import utils.PageMaker;

public class NoticeServiceImpl implements NoticeService{

	NoticeDAO dao = new NoticeDAOImpl();
	
	
	@Override
	public boolean noticeWrite(HttpServletRequest request) {
		NoticeVO vo = new NoticeVO();
		vo.setNotice_author(request.getParameter("notice_author"));
		vo.setNotice_category(request.getParameter("notice_category"));
		vo.setNotice_title(request.getParameter("notice_title"));
		vo.setNotice_content(request.getParameter("notice_content"));
		boolean result = dao.noticeWrite(vo);
		
		return result;
	}

	@Override
	public void noticeList(HttpServletRequest request) {
		int defaultPage = 1;
		String page = request.getParameter("page");
		if(page != null) {
			defaultPage = Integer.parseInt(page);
		}
		PageMaker pm = new PageMaker();
		pm.getCri().setPage(defaultPage);
		pm.setDisplayPageNum(10);
		int totalCount = dao.getTotalCount();
		pm.setTotalCount(totalCount);
		request.setAttribute("pm", pm);

		List<NoticeVO> noticeList = dao.noticeList(pm.getCri().getStartRow()
				,pm.getCri().getPerPageNum());
		
		request.setAttribute("noticeList", noticeList);
	}

	@Override
	public void noticeDetail(HttpServletRequest request) {
		int num = Integer.parseInt(request.getParameter("notice_num"));
		NoticeVO vo = dao.noticeDetail(num);
		request.setAttribute("notice", vo);
	}

	@Override
	public boolean noticeUpdate(HttpServletRequest request) {
		NoticeVO vo = new NoticeVO();
		int num = Integer.parseInt(request.getParameter("notice_num"));
		vo.setNotice_num(num);
		vo.setNotice_author(request.getParameter("notice_author"));
		vo.setNotice_category(request.getParameter("notice_category"));
		vo.setNotice_title(request.getParameter("notice_title"));
		vo.setNotice_content(request.getParameter("notice_content"));
		boolean result = dao.noticeUpdate(vo);
		
		return result;
	}

	@Override
	public boolean noticeDelete(HttpServletRequest request) {
		int num = Integer.parseInt(request.getParameter("notice_num"));
		boolean result = dao.noticeDelete(num);
		
		return result;
	}

	
	
}
