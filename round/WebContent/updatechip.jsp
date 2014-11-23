<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<jsp:useBean id="player" class="tom.PlayerInfo" scope="session"/>

<%
	Integer lost = (Integer)request.getAttribute("lostchip");
	Integer win = (Integer)request.getAttribute("winchip");
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title> update remain chip </title>
</head>
<body>

	<jsp:setProperty name="player" property="userchip" value="<%=player.getUserchip()-lost+win%>"/>
	
	
	<form action="GameServer" method="get">
	<input type="text" name="userchip" value="<%=player.getUserchip()%>"/>
    <input type="submit" value="이동"/>
      </form>
	
	
		
	
</body>
</html>