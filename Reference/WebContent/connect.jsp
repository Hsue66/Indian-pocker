<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<jsp:useBean id="player" class="tom.PlayerInfo" scope="session"/>	<%-- player�� playerInfo �� ��ü --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>~~~~~~~~~~~~~</title>
</head>
<body>					<%-- name�� servlet������ ������, value�� �Ѱ��� �� player.getUserid() --%>
	<jsp:forward page = "GameServer">						
		<jsp:param name="userid" value="<%=player.getUserid()%>"/>		
		</jsp:forward>
</body>					<%-- �г��� ������ ������ GameServer ���� --%>
</html>