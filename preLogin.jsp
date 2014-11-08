<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title> Create Session </title>					<%-- 사용자의 세션 설정 --%>

</head>
<body>
<%	
	String id = request.getParameter("nick");	
	session.setAttribute("nick", id);			// 닉네임을 attribute로  저장
%>
	<jsp:forward page="WaitingPage.jsp"/>		<%--바로 대기화면 페이지로 넘어감--%>
	 
</body>


</html>