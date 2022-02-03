<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>	
<%
	String point = (String)request.getAttribute("point");
%>

{"POINT": <%= point %> }
