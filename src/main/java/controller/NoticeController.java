package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.NoticeService;
import service.NoticeServiceImpl;

@WebServlet("*.do")
public class NoticeController extends HttpServlet{

	NoticeService ns = new NoticeServiceImpl();
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String requestPath = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestPath.substring(contextPath.length()+1);
		
		String view = "";
		String success = "/board/notice/notice_success.jsp";
		String fail = "/board/notice/notice_fail.jsp";
		
		if(command.equals("notice.do")) {
			ns.noticeList(request);
			view = "/board/notice/notice_list.jsp";
			
		} else if (command.equals("noticeWrite.do")) {
			view = "/board/notice/notice_write.jsp";
			
		} else if (command.equals("noticeUpdate.do")) {
			ns.noticeDetail(request);
			view = "/board/notice/notice_update.jsp";
			
		} else if (command.equals("noticeDetail.do")) {
			ns.noticeDetail(request);
			view = "/board/notice/notice_detail.jsp";
		
		} else if (command.equals("noticeDelete.do")) {
			view = ns.noticeDelete(request) ? success : fail;
			
		} else if (command.equals("noticeWriteSubmit.do")) {
			view = ns.noticeWrite(request) ? success : fail; 
			
		} else if (command.equals("noticeUpdateSubmit.do")) {
			view = ns.noticeUpdate(request) ? success : fail;
			
		} 
		
		if(view != null && !view.equals("")){
			request.getRequestDispatcher(view).forward(request, response);
		}
	}	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}	
	
}
