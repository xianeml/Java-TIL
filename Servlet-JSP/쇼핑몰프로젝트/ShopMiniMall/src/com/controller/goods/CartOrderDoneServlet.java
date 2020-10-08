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
import com.dto.OrderDTO;
import com.service.CartService;
import com.service.GoodsService;
import com.service.MemberService;

/**
 * Servlet implementation class GoodsListServlet
 */
@WebServlet("/CartOrderDoneServlet")
public class CartOrderDoneServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
		//로그인 확인
		//데이터 파싱 OrderDTO 생성, orderNum= 장바구니 번호 
		//Cartservice.orderDone(OrderDTO, orderNum)=>주문정보저장, 장바구니 번호 이용 카트 삭제
		//두 작업시 trasaction처리 
		//request에 OrderDTO저장 orderDone.jsp로 이동 
		//비회원 처리 

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
