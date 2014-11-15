<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="KSC5601"%>

<jsp:useBean id="player" class="tom.PlayerInfo" scope="session"/>
<!-- <jsp:setProperty name="player" property="*"/> 사용시 7번줄 선언 불필요
	 setProperty 사용시 param=""으로 값 저장 -->

<% 
String userid= request.getParameter("userid");		// 입력받은 닉네임을 string에 저장(18번줄에 사용)
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<title>	 Login  </title>	
</head>
<body>
<%
	if(userid.isEmpty()==false) 					// 닉네임을 입력했을시
	{
%>		<jsp:setProperty name="player" property="userid"/>	<!-- 닉네임을 bean클래스로 저장-->
		<jsp:getProperty name="player" property="userid"/> 님 게임에 입장하시겠습니까? <br><br>
		<a href ="connect.jsp"> 게임방 입장 </a><br>			<!-- 게임방입장 링크 -->
		<a href ="login.html"> 처음으로	</a><br>			<!-- 로그인화면 링크 -->
<%	}
	else											// 닉네임을 입력하지 않았을 시 
	{ 												// 경고창 출력
%>	<script type="text/javascript">alert("닉네임을 입력하세요!!");history.back();</script>
<%  }%>
</body>
</html>
