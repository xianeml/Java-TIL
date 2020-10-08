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

/**
 * Servlet implementation class GoodsListServlet
 */
@WebServlet("/CartDelAllServlet")
public class CartDelAllServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//로그인 정보 확인
		HttpSession session = request.getSession();
		MemberDTO dto = (MemberDTO)session.getAttribute("login");
		String nextPage = null;
		if(dto!=null) {
			String data = request.getParameter("data");
			System.out.println(data);
		String [] x = data.split(",");
		List<String> list = Arrays.asList(x);
		
		CartService service = new CartService();
		int n = service.cartAllDel(list);
		nextPage = "CartListServlet";
		
		}else {
			nextPage = "LoginUIServlet";
			session.setAttribute("mesg", "로그인이 필요한 작업입니다.");
		}
		response.sendRedirect(nextPage);
		
//data파싱--- 10,20,30
//데이터룰 , 를 기준으로 list에 저장 
//cartService.cartAllDel(list)전달  ==> mapper id cartAlldel 동적 sql사용
//db수정후 CartListServlet 사용 카트 목록 다시 띄우기 (모두 삭제 후이므로 데이터가 없음)

//회원이 아닌 경우 회원가입페이지로 이동 
		

		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
