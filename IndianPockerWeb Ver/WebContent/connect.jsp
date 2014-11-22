<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<jsp:useBean id="player" class="tom.PlayerInfo" scope="session"/>	<%-- player는 playerInfo 의 객체 --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>~~~~~~~~~~~~~</title>
</head>
<body>					<%-- name은 servlet에서의 변수명, value는 넘겨줄 값 --%>
	<FORM METHOD=GET ACTiON="GameServer" >	
		<INPUT TYPE="hidden" name="userid" value="<%=player.getUserid()%>">	
		<input type="submit" value="gogo">				
	</FORM>
</body>					<%-- 닉네임정보와 칩들을 가지고 Game 입장 --%>
</html>