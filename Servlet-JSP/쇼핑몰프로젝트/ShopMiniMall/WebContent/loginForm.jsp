<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.dto.MemberDTO"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
<%
	String mesg = (String)session.getAttribute("mesg");
	MemberDTO dto = (MemberDTO)session.getAttribute("login");
%>
if(dto==null){
	alert(mesg);
}
</script>
</head>
<body>

<h1>로그인 폼 화면입니다.</h1>
<jsp:include page="common/top.jsp" flush="true"></jsp:include><br>
<jsp:include page="common/menu.jsp" flush="true"></jsp:include>
<hr>
<!-- include -->
<jsp:include page="member/loginForm.jsp"></jsp:include>
</body>
</html>