<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="KSC5601"%>

<jsp:useBean id="player" class="tom.PlayerInfo" scope="session"/>
<!-- <jsp:setProperty name="player" property="*"/> 사용시 8번줄 선언 불필요
	 setProperty 사용시 param=""으로 값 저장 -->

<% 
String userid= request.getParameter("userid");		// 입력받은 닉네임을 string에 저장(18번줄에 사용)
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>	 Login  </title>	
</head>
<body>
<%
	if(userid.isEmpty()==false) 					// 닉네임을 입력했을시
	{
%>		<jsp:setProperty name="player" property="userid"/>	<!-- 닉네임을 bean클래스로 저장-->
		<jsp:getProperty name="player" property="userid"/> 님 게임에 입장하시겠습니까? <br><br>
		<FORM METHOD=GET ACTiON="GameServer" >	
		<INPUT TYPE="hidden" name="userid" value="<%=player.getUserid()%>">			<!--  id를 gameserver로 넘겨줌 -->
		<input type="submit" value="게임방 입장">				
		</FORM>
		<FORM METHOD=GET ACTiON="login.html" >	
		<input type="submit" value="로그인창으로">				
		</FORM>
		
<%	}
	else											// 닉네임을 입력하지 않았을 시 
	{ 												// 경고창 출력
%>	<script type="text/javascript">alert("닉네임을 입력하세요!!");history.back();</script>
<%  }%>
</body>
</html>
