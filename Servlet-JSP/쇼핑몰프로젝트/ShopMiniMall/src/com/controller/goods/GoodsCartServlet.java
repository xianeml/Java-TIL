package com.controller.goods;

import java.io.IOException;
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

@WebServlet("/GoodsCartServlet")
public class GoodsCartServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// login 정보 확인 후 데이터파싱
		HttpSession session = request.getSession();
		MemberDTO dto = (MemberDTO) session.getAttribute("login");
		String nextPage = null;
		// 있느경우 데이터파싱 쭉 함. 그걸로 카트 디티오 만들어서 셋 해줌
		if (dto != null) {
			String userid = dto.getUserid();
			String gCode = request.getParameter("gCode");
			String gName = request.getParameter("gName");
			String gPrice = request.getParameter("gPrice");
			String gSize = request.getParameter("gSize");
			String gColor = request.getParameter("gColor");
			String gAmount = request.getParameter("gAmount");
			String gImage = request.getParameter("gImage");

			// CartDTO생성

			CartDTO cart = new CartDTO();
			cart.setUserid(userid);
			cart.setgCode(gCode);
			cart.setgName(gName);
			cart.setgPrice(Integer.parseInt(gPrice));
			cart.setgSize(gSize);
			cart.setgColor(gColor);
			cart.setgAmount(Integer.parseInt(gAmount));
			cart.setgImage(gImage);

			// CartService.cartAdd사용 데이터 insert 후
			CartService service = new CartService();
			int n = service.cartAdd(cart);
			// GoodsRetrieve에 gCode요청으로 상품자세히 보기 화면 재출력 (메세지 전송 )
			// 세션에 메세지키에 지코드랑 카트저장성공메세지 저장.
			nextPage = "GoodsRetrieveServlet?gCode=" + gCode;
			session.setAttribute("mesg", gCode + "카트저장성공");
		} else {
			// 로그인 정보가 없을 경우 로그인 화면으로 //로그인ui서블릿//메세지키에 로그인필요하다고알려
			nextPage = "LoginUIServlet";
			session.setAttribute("mesg", "로그인 필요");
		}

		// 응답은 넥스트페이지
		// 경고창에 장바구니 담기 성공 메세지 출력
		response.sendRedirect(nextPage);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
