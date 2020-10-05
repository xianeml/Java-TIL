<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.dto.MemberDTO"%>

<%
   	MemberDTO dto = (MemberDTO)session.getAttribute("login");
    if(dto != null){
    %>
<a href="">로그아웃</a>
<a href="">MyPage</a>
<% 
    }else{	
%>
<a href="">로그인</a>
<a href="MemberUIServlet">회원가입</a>
<a href="">장바구니</a>
<%
    }
%>
