<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String id=null;%>
<%! String id2=null;%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<title>INDIAN POCKER</title>
		<link rel="stylesheet" href="style.css"/>
	</head>
	<body>
	<%
		String index = request.getParameter("index");
		int a = Integer.parseInt(index);
		if(a==1)
		{
			id=(String)request.getAttribute("id");
			id = request.getParameter("id");
		}else
		{
			id2=(String)request.getAttribute("id");
			id2 = request.getParameter("id");
		}
		System.out.println(a);
	%>
		<div class = "container">
		
			<div class = "header" align="center" >
				<h1>INDIAN POCKER</h1>
			</div>
			
			<div class = "my_id" align="center">
				<h2><%=id%></h2> 
			</div>
			
			<div class = "other_id" align="center">
				<h2><%=id2%></h2>  
			</div>
			
			<div class = "my_card" align="center">
				<h1>MY CARD</h1>
			</div>
			
			<div class = "my_info" align="center">
				<h1>MY INFO</h1>
			</div>
			
			<div class = "center" align="center">
				<h1>SYSTEM INFO</h1>
			</div>
			
			<div class = "other_card" align="center">
				<h1>OTHER CARD</h1>
			</div>
			
			<div class = "other_info" align="center">
				<h1>OTHER INFO</h1>
			</div>
			
			<div class = "fooster" align="center">
				<button type="button" onClick="Gamepage.jsp;">
				<h1>CHECK</h1></button>
			</div>
			
		</div>
	</body>
</html>

