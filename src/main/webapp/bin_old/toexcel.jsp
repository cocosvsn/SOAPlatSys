<%@ page language="java" contentType="application/msexcel; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
response.setHeader("Content-disposition","attachment; filename="+request.getParameter("filename")+".xls");
String str = request.getParameter("htmltable");
out.print(str);
%>