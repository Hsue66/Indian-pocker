<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<jsp:useBean id="player" class="tom.PlayerInfo" scope="session"/>	<%-- player는 playerInfo 의 객체 --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>~~~~~~~~~~~~~</title>
</head>
<body>					<%-- name은 servlet에서의 변수명, value는 넘겨줄 값 player.getUserid() --%>
	<jsp:forward page = "GameServer">						
		<jsp:param name="userid" value="<%=player.getUserid()%>"/>		
		</jsp:forward>
</body>					<%-- 닉네임 정보를 가지고 GameServer 접속 --%>
</html>