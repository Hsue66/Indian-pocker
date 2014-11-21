<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<jsp:useBean id="player" class="tom.PlayerInfo" scope="session"/>
<jsp:useBean id="enemy" class="tom.PlayerInfo" scope="session"/>


<%
	Integer mycard = (Integer)request.getAttribute("mycard");
	Integer myleftchip = (Integer)request.getAttribute("myleftchip");
	Integer mybetchip = (Integer)request.getAttribute("mybetchip");
	
	String enemyid = (String)request.getAttribute("enemyid");
	
	Integer enemycard = (Integer)request.getAttribute("enemycard");
	Integer enemyleftchip = (Integer)request.getAttribute("enemyleftchip");
	Integer enemybetchip = (Integer)request.getAttribute("enemybetchip");
		
	if(mycard!=null)%><jsp:setProperty name="player" property="usercard" value="<%=mycard%>"/>	
<%	if(myleftchip!=null)%><jsp:setProperty name="player" property="userchip" value="<%=myleftchip%>"/>
<%	if(mybetchip!=null)%><jsp:setProperty name="player" property="betchip" value="<%=mybetchip%>"/>

<%	if(enemyid!=null)%><jsp:setProperty name="enemy" property="userid" value="<%=enemyid%>"/>
<%	if(enemycard!=null)%><jsp:setProperty name="enemy" property="usercard" value="<%=enemycard%>"/>	
<%	if(enemyleftchip!=null)%><jsp:setProperty name="enemy" property="userchip" value="<%=enemyleftchip%>"/>
<%	if(enemybetchip!=null)%><jsp:setProperty name="enemy" property="betchip" value="<%=enemybetchip%>"/>


	


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<title>INDIAN POCKER</title>
			<link rel="stylesheet" href="style.css"/>
	</head>
	<body>
		<div class = "container">
		
			<div class = "my_id" align="center">
				<h2><jsp:getProperty name="player" property="userid"/></h2> 
			</div>		
		
			<div class = "header" align="center" >
				<h1></h1>
			</div>
			
			<div class = "other_id" align="center">
				<h2><jsp:getProperty name="enemy" property="userid"/></h2> 
			</div>
			
			<div class = "my_card" align="center">
				<h1>MY CARD</h1>
			</div>
			
			<div class = "my_info" align="center">
				<h1><jsp:getProperty name="player" property="userchip"/></h1>
			</div>

		<div class="state" align="center">
			
				상대가 베팅한 칩:<jsp:getProperty name="enemy" property="betchip" />
			
		</div>

		<div class="bet" align="center">
			<FORM METHOD=GET ACTiON="GameServer">
				<!-- submit 누를시 이동할 페이지 -->
				베팅할 칩: <INPUT TYPE="text" name="betchip"> <INPUT
					TYPE="hidden" name="checkid" value="<%=player.getUserid()%>">
				<input type="submit" value="베팅">
			</FORM>
			<FORM METHOD=GET ACTiON="GameServer">
				<INPUT TYPE="hidden" name="checkenemybet" value="kk"> <INPUT
					TYPE="hidden" name="checkid" value="<%=player.getUserid()%>">
				<input type="submit" value="상대베팅확인">
			</FORM>
		</div>

		<div class="other_card" align="center">
			<h1><jsp:getProperty name="enemy" property="usercard" /></h1>
		</div>

		<div class = "other_info" align="center">
				<h1><jsp:getProperty name="enemy" property="userchip"/></h1>
				
			</div>
			
		</div>
	</body>
</html>
