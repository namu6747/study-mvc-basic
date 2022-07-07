package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MemberService;
import service.MemberServiceImpl;

public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	MemberService ms = new MemberServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		// Cookie 정보 확인 - 자동 로그인
		MemberService.loginCheck(request);
		
		String requestPath = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestPath.substring(contextPath.length()+1);
		System.out.println("MemberController 요청 : "+command);
		
		String view = "";
		
		if(command.equals("join.mc")) {
			view = "/member/join.jsp";
		}
		
		if(command.equals("joinSubmit.mc")) {
			System.out.println("회원 가입 처리 요청");
			ms.memberJoin(request, response);
		}
		
		if(command.equals("login.mc")) {
			view = "/member/login.jsp";
		}
		
		if(command.equals("loginSubmit.mc")) {
			System.out.println("로그인 처리 요청");
			boolean isLogin = ms.memberLogin(request, response);
			if(isLogin) {
				// 로그인 성공
				System.out.println("로그인 성공");
				response.sendRedirect(contextPath+"/test");
			}else {
				// 로그인 실패
				System.out.println("로그인 실패");
				response.sendRedirect(contextPath+"/login.mc");
			}
		}
		
		if(command.equals("info.mc")) {
			view = "/member/info.jsp";
		}
		
		if(command.equals("update.mc")) {
			view = "/member/update.jsp";
		}
		
		if(command.equals("updateSubmit.mc")) {
			System.out.println("회원 정보 수정 요청 처리 ");
			ms.memberUpdate(request, response);
		}
		
		if(command.equals("logOut.mc")){
			System.out.println("로그아웃 처리 요청");
			ms.logOut(request, response);
			// 요청 처리 
			request.setAttribute("test", "로그아웃 완료");
			view = "/common/main.jsp";
		}
		
		if(command.equals("withdraw.mc")) {
			System.out.println("회원 탈퇴 요청 처리");
			// 비밀번호를 다시 입력받아 비밀번호가 일치할 경우 탈퇴 처리;
			view = "/member/withdraw.jsp";
		}
		
		if(command.equals("withdrawSubmit.mc")) {
			System.out.println("회원 탈퇴 요청 처리");
			// 비밀번호를 다시 입력받아 비밀번호가 일치할 경우 탈퇴 처리;
			ms.withDraw(request, response);
		}
		
		/*
		 * 비밀번호 찾기
		 */
		if(command.equals("findPass.mc")) {
			view = "/member/findPass.jsp";
		}
		
		if(command.equals("findPassSubmit.mc")) {
			System.out.println("비밀번호 찾기 메일 발송");
			ms.findPassSubmit(request, response);
		}
		
		if(command.equals("passAccept.mc")) {
			System.out.println("코드 확인");
			ms.changePassCode(request, response);
		}
		
		if(command.equals("changePassSubmit.mc")) {
			System.out.println("비밀번호 변경 요청");
			ms.changePass(request, response);
		}
		
		if(view != null && !view.equals("")){
			request.getRequestDispatcher(view).forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}









