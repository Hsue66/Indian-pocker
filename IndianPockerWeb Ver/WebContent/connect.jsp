<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<jsp:useBean id="player" class="tom.PlayerInfo" scope="session"/>	<%-- player�� playerInfo �� ��ü --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>~~~~~~~~~~~~~</title>
</head>
<body>					<%-- name�� servlet������ ������, value�� �Ѱ��� �� --%>
	<FORM METHOD=GET ACTiON="GameServer" >	
		<INPUT TYPE="hidden" name="userid" value="<%=player.getUserid()%>">	
		<input type="submit" value="gogo">				
	</FORM>
</body>					<%-- �г��������� Ĩ���� ������ Game ���� --%>
</html>