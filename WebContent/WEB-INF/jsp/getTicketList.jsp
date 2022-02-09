<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>	
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Optional"%>
<%@page import="java.util.List"%>
    
    <%
    Optional<List<String[]>> optList =Optional.ofNullable((List<String[]>) request.getAttribute("LIST"));
    List<String[]> list= new ArrayList<>();
    if(optList.isPresent()){
    list = optList.get();	 
    }
    %>

<%
for(String[] s:list){
%>
{"ID":ticket_id,"OptName":ticket_name,"POINT":point},

<%}%>

