<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../includes/header.jsp" %>

 <div class="row">
 	<div class="col-lg-12">
    	<h1 class="page-header">Board Register</h1>
    </div>
</div>

 <div class="row">
 	<div class="col-lg-12">
	    <div class="panel panel-default">
	    
	        <div class="panel-heading">Board Read Page</div>
	        <!-- /.panel-heading -->
	        <div class="panel-body">
	        		<div class="form-group">
	        			<label>Bno</label>
	        			<input class="form-control" name="bno"
	        			value='<c:out value="${board.bno}" />' readonly="readonly">
	        		</div>
	        		<div class="form-group">
	        			<label>Title</label>
	        			<input class="form-control" name="title"
	        			value='<c:out value="${board.title}" />' readonly="readonly">
	        		</div>
	        		<div class="form-group">
	        			<label>Text area</label>
	        			<textarea class="form-control" rows="3" name="content"
	        			 readonly="readonly"><c:out value="${board.content}" /></textarea>
	        		</div>
	        		<div class="form-group">
	        			<label>Writer</label>
	        			<input class="form-control" name="writer"
	        			value='<c:out value="${board.writer}" />' readonly="readonly">
	        		</div>
	        		<button data-oper='modify' class="btn btn-default">
	        			Modify</button>
	        		<button data-oper='list' class="btn btn-default">
	        			List</button>
	        			
	        		<form id="operForm" action="/board/modify" method="get">
	        			<input type="hidden" id="bno" name="bno" value='<c:out value="${board.bno}"/>'>
	        			<input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum}"/>'>
	        			<input type="hidden" name="amount" value='<c:out value="${cri.amount}"/>'>
	        		</form>
<%-- 	        		<button data-oper='modify' class="btn btn-default"
	        			onclick="location.href='/board/modify?bno=<c:out value="${board.bno}"/>'">
	        			Modify</button>
	        		<button data-oper='list' class="btn btn-default"
	        			onclick="location.href='/board/list'">
	        			List</button> --%>
	        </div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(() => {
		const operForm = $("#operForm");
		$("button[data-oper='modify']").on("click", (e) => {
			operForm.attr("action", "/board/modify").submit();
		});
		
		$("button[data-oper='list']").on("click", (e) => {
			operForm.find("#bno").remove();
			operForm.attr("action", "/board/list");
			operForm.submit();
		});
		
	})
</script>

<%@include file="../includes/footer.jsp" %>
