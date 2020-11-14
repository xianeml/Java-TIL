<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${!empty mesg}">
   <script>
     alert('${mesg}');
   </script>
</c:if>

<script type="text/javascript" src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
   
    	 $("form").on("submit",function(event){		
    		 var userid = $("#userid").val();
    		 var passwd = $("#passwd").val();
    	    		if(userid.length==0){
    	    			alert("userid 필수")
    	    			$("#userid").focus();
    	    			event.preventDefault();
    	    		}else if(passwd.length==0){
    	    			alert("passwd 필수")
    	    			$("#passwd").focus();
    	    			event.preventDefault();
    	    		}
    	    	});
   });
</script>    


<form action="login" method="post">
아이디:<input type="text" name="userid" id="userid"><br>
비밀번호:<input type="text" name="passwd" id="passwd"><br> 
<input type="submit" value="로그인">
<input type="reset" value="취소">
<a href="">아이디찾기</a>
</form>
