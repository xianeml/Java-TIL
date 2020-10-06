package com.controller.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dto.MemberDTO;
import com.service.MemberService;

/**
 * Servlet implementation class MemberUIServlet
 */
@WebServlet("/MemberUpdateServlet")
public class MemberUpdateServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 
		HttpSession session = request.getSession();
		MemberDTO dto = (MemberDTO) session.getAttribute("login");
		String nextPage = null;
		if (dto != null) {
			request.setCharacterEncoding("utf-8");
			String userid = request.getParameter("userid");
			String passwd = request.getParameter("passwd");
			String username = request.getParameter("username");
			String post = request.getParameter("post");
			String addr1 = request.getParameter("addr1");
			String addr2 = request.getParameter("addr2");
			String phone1 = request.getParameter("phone1");
			String phone2 = request.getParameter("phone2");
			String phone3 = request.getParameter("phone3");
			String email1 = request.getParameter("email1");
			String email2 = request.getParameter("email2");
			MemberDTO dto2 = new MemberDTO(userid, passwd, username, post, addr1, addr2,
			phone1, phone2, phone3, email1, email2);
			MemberService service = new MemberService();
			int n = service.memberUpdate(dto2);
			nextPage = "main";
		}else {
			nextPage = "LoginUIServlet";
			session.setAttribute("mesg", "로그인이 필요한 작업입니다.");
		}		
		response.sendRedirect(nextPage);	

	//세션에서 로그인 여부 검사 
	// 회원인 경우만 데이터파싱, service.memberUpdate(MemberDTO)전송 업데이트 후 메인으로 이동
	//회원이 아닌 경우 "mesg"에 로그인 필요한 작업입니다. 메세지 띄우고 로그인화면으로 이동 

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
