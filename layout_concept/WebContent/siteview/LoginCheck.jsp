<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! int index=1; %>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%
	
	String id = request.getParameter("id");
	request.setAttribute("id", id);

	//request.setAttribute("index", index);
	
	System.out.println(index);
	if(index>2)
		response.sendRedirect("Error.html");
	else
		pageContext.forward("Gamepage.jsp?index="+index);
	
	if(index<2)
		index++;
	%>
	
</body>
</html>
<html>