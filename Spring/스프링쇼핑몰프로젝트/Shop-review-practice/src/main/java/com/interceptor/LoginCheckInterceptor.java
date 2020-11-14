package com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle 동작 ======");
		HttpSession session = request.getSession();
		if(session.getAttribute("login")==null) {
			response.sendRedirect("../loginForm");
			return false;
		}else {
			return true;
		}
	}
	
}
