package com.controller.goods;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dto.CartDTO;
import com.dto.GoodsDTO;
import com.dto.MemberDTO;
import com.service.CartService;
import com.service.GoodsService;
import com.service.MemberService;

/**
 * Servlet implementation class GoodsListServlet
 */
@WebServlet("/CartOrderConfirmServlet")
public class CartOrderConfirmServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDTO dto = (MemberDTO)session.getAttribute("login");
		String nextPage = null;
		if(dto!=null) {
			String num = request.getParameter("num");
			CartService cService = new CartService();
			CartDTO cdto = cService.cartByNum(Integer.parseInt(num));
			String userid = dto.getUserid();
			MemberService mService = new MemberService();
			MemberDTO mdto = mService.mypage(userid);
			request.setAttribute("cdto", cdto);
			request.setAttribute("mdto", mdto);
			nextPage = "orderConfirm.jsp";
		}else {
			nextPage = "LoginUIServlet";
			session.setAttribute("mesg","로그인필요");
		}		
		RequestDispatcher dis = request.getRequestDispatcher(nextPage);
		dis.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
