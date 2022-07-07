package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.MemberVO;
import repositories.MemberDAO;
import repositories.MemberDAOImpl;
import utils.GoogleAuthentication;

public class MemberServiceImpl implements MemberService{
	
	MemberDAO dao = new MemberDAOImpl();

	@Override
	public void memberJoin(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String rePass = request.getParameter("rePass");
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String gender = request.getParameter("gender");
		MemberVO vo = new MemberVO(id,pass,name,age,gender);
		
		
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter pw = response.getWriter();
			pw.print("<script>");
			if(!pass.equals(rePass)) {
				pw.print("alert('비밀번호가 일치하지 않습니다.');");
				pw.print("history.go(-1);");
				pw.print("</script>");
				return;
			}
			
			MemberVO member = dao.getMemberById(id);
			if(member != null) {
				pw.print("alert('이미 존재하는 사용자 정보 입니다.');");
				pw.print("history.go(-1);");
				pw.print("</script>");
				return;
			}
			
			boolean isSuccess = dao.memberJoin(vo); 
			
			if(isSuccess) {
				// 회원가입 성공
				pw.print("alert('회원가입 성공');");
				pw.print("location.href='login.mc';");
			}else {
				// 회원가입 실패
				pw.print("alert('회원가입 실패');");
				pw.print("history.go(-1);");
			}
			pw.print("</script>");
		} catch (IOException e) {}
	}

	@Override
	public boolean memberLogin(HttpServletRequest request, HttpServletResponse response) {
		boolean isLogin = false;
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String check = request.getParameter("check");
		System.out.println("msi 72");
		MemberVO member = dao.memberLogin(id, pass);
		if(member != null) {
			isLogin = true;
			HttpSession session = request.getSession();
			session.setAttribute("member", member);
			if(check != null) {
				Cookie cookie = new Cookie("id",member.getId());
				cookie.setMaxAge(60*60*24*15);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		return isLogin;
	}

	@Override
	public void memberUpdate(HttpServletRequest request, HttpServletResponse response) {
		MemberVO member = new MemberVO(
				request.getParameter("id"),
				request.getParameter("pass"),
				request.getParameter("name"),
				Integer.parseInt(request.getParameter("age")),
				request.getParameter("gender")
			);
		member.setNum(Integer.parseInt(request.getParameter("num")));
		
		boolean isUpdate = dao.memberUpdate(member);
		
		String url = "info.mc";
		String msg = "회원정보 수정완료";
		
		if(isUpdate) {
			// 수정 완료
			MemberVO vo = dao.getMemberById(member.getId());
			request.getSession().setAttribute("member", vo);
		}else {
			// 수정 실패
			url = "update.mc";
			msg = "회원정보 수정 실패";
		}
		
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('"+msg+"');");
			out.print("location.href='"+url+"';");
			out.print("</script>");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void logOut(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("member");
		Cookie cookie = new Cookie("id","");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	@Override
	public void withDraw(HttpServletRequest request, HttpServletResponse response) {
		String tempPass = request.getParameter("tempPass");
		System.out.println("tempPass : " + tempPass);
		
		MemberVO member 
			= (MemberVO)request.getSession().getAttribute("member");
		
		response.setContentType("text/html;charset=utf-8");
		
		try {
			PrintWriter pw = response.getWriter();
			pw.print("<script>");
			if(member != null && member.getPass().equals(tempPass)) {
				dao.withDrawMember(member.getNum());
				logOut(request, response);
				pw.print("location.href='test';");
			}else {
				// 회원탈퇴 실패
				pw.print("alert('회원탈퇴 실패!');");
				pw.print("history.go(-1);");
			}
			pw.print("</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	// 비밀번호 찾기
	@Override
	public void findPassSubmit(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		
		boolean isCheck = dao.checkMember(id, name);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			if(!isCheck) {
				System.out.println("일치하는 정보 없음");
				throw new NullPointerException("일치하는 사용자 정보가 없음");
			}
			
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i<5; i++) {
				int random = (int)(Math.random()*10);
				sb.append(random);
			}
			String code = sb.toString();
			// 메일 발송전 DB에 저장
			dao.addPassCode(id, code);
			System.out.println("등록완료");
			
			// 메일 발송
			GoogleAuthentication ga = new GoogleAuthentication();
			Session session = Session.getInstance(ga.getProp(), ga);
			MimeMessage msg = new MimeMessage(session);
			InternetAddress toAddress = new InternetAddress(id);
			InternetAddress fromAddress = new InternetAddress(
					"hap0p9y@nate.com" , "관리자");
			msg.setSentDate(new Date());
			msg.setHeader("Content-Type", "text/html;charset=utf-8");
			msg.setRecipient(Message.RecipientType.TO, toAddress);
			msg.setFrom(fromAddress);
			msg.setSubject("비밀번호 찾기!!","utf-8");
			StringBuilder mail = new StringBuilder();
			mail.append("<!DOCType html>");
			mail.append("<html>");
			mail.append("<head>");
			mail.append("<meta charset='utf-8'>");
			mail.append("</head>");
			mail.append("<body>");
			mail.append("<h1>@@@ 사이트 비밀번호 찾기 이메일 인증!</h1>");
			mail.append("<form action='http://192.168.1.113:8080");
			mail.append(request.getContextPath());
			mail.append("/passAccept.mc' method='post' ");
			mail.append(" onsubmit='window.open(\"\",\"w\")' target='w'>");
			mail.append("<input type='hidden' name='id' value='"+id+"'/>");
			mail.append("<input type='hidden' name='code' value='"+code+"'/>");
			mail.append("<input type='submit' value='이메일 인증 완료' />");
			mail.append("</form>");
			mail.append("</body>");
			mail.append("</html>");
			String content = mail.toString();
			System.out.println(content);
			msg.setContent(content,"text/html;charset=utf-8");
			
			Transport.send(msg);
			
			out.print("<script>");
			out.print("alert('메일이 정상적으로 전송되었습니다. 메일을 확인해주세요.');");
			out.print("location.href='test';");
			out.print("</script>");
			
		} catch (Exception e) {
			e.printStackTrace();
			out.print("<script>");
			out.print("alert('서비스에 문제가 있습니다. 다시 이용해 주세요."+e.getMessage()+"');");
			out.print("location.href='login.mc';");
			out.print("</script>");
		}
		
		
	}

	@Override
	public void changePassCode(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		
		System.out.println(id+" : "+ code);
		
		boolean isCheck = dao.checkPassCode(id, code);
		try {
			response.setContentType("text/html;charset=utf-8");
			if(isCheck) {
				System.out.println("일치");
				request.setAttribute("id", id);
				request.setAttribute("code", code);
				request.getRequestDispatcher("/member/changePass.jsp")
				.forward(request, response);
			}else {
				PrintWriter pw = response.getWriter();
				pw.print("<script>");
				pw.print("alert('잘못된 요청입니다.');");
				pw.print("location.href='login.mc';");
				pw.print("</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void changePass(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		String pass = request.getParameter("pass");
		
		boolean isCheck = dao.checkPassCode(id, code);
		
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print("<script>");
			if(isCheck) {
				dao.changePass(id, pass);
				pw.print("alert('변경 요청 처리 완료');");
			}else {
				pw.print("alert('올바른 접근이 아닙니다.');");
			}
			pw.print("location.href='login.mc';");
			pw.print("</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}





