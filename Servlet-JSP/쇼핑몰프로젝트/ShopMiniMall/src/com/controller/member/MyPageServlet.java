package com.controller.member;

import java.io.IOException;
import java.util.HashMap;

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
@WebServlet("/MyPageServlet")
public class MyPageServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		MemberDTO dto = (MemberDTO) session.getAttribute("login");
		String nextPage = null;
		if (dto != null) {
			nextPage = "mypage.jsp";
			String userid = dto.getUserid();
			MemberService service = new MemberService();
			MemberDTO x = service.mypage(userid);
			session.setAttribute("login", x);
		}else {
			nextPage = "LoginUIServlet";
		}		
		response.sendRedirect(nextPage);	
		// 세션에서 login 가져오기
		// login정보가 있는 경우 service.mypage(userid)를 이용 사용자 정보
		// db에서 가져와 MemberDTO로 저장 Session에 "login"으로 저장 mypage.jsp 로 이동
		// login정보가 없는 경우 LoginUISerlvet 요청 화면 로그인화면으로 화면 전환

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
