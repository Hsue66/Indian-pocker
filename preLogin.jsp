<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title> Create Session </title>					<%-- ������� ���� ���� --%>

</head>
<body>
<%	
	String id = request.getParameter("nick");	
	session.setAttribute("nick", id);			// �г����� attribute��  ����
%>
	<jsp:forward page="WaitingPage.jsp"/>		<%--�ٷ� ���ȭ�� �������� �Ѿ--%>
	 
</body>


</html>