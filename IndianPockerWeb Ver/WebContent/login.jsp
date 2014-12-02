<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="KSC5601"%>

<jsp:useBean id="player" class="tom.PlayerInfo" scope="session"/>
<!-- <jsp:setProperty name="player" property="*"/> 사용시 8번줄 선언 불필요
	 setProperty 사용시 param=""으로 값 저장 -->

<% 
String userid= request.getParameter("userid");		// 입력받은 닉네임을 string에 저장(18번줄에 사용)
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">	<!-- 여기가 시작 -->
<title> Welcome </title>
</head>

<style>
body 
{
	margin:0 auto;
	background:black;
	background-attachment:fixed;
}
</style>

<body>

<div align="center" style="border: height: 100px; padding: 700px 0px 0px 0px;
 margin:0 auto; background-image:url(./img/login.jpg); background-repeat:no-repeat;
background-position: 50% 50%; background-size: contain; " >
</div>

<div align="center" style="border: height: 100px; padding: 10px 0px 0px 0px;
 margin:0 auto; " >


<font color = "white" size="3px">사용하실 닉네임을 입력해주십시오</font>	
	<FORM METHOD=POST ACTiON="login.jsp" >				<!-- submit 누를시 이동할 페이지 -->
	닉네임 : <INPUT TYPE="text" name="userid" size="15">	<!-- name = login.jsp로 넘길변수-->
	<input type="submit" value="접속">					<!-- submit버튼 표시내용="접속" -->
	</FORM>
	
</div>

</body>
</html>
