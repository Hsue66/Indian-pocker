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
<title> print player id </title>
</head>
<body>
	
	
	�����̵�:<jsp:getProperty name="player" property="userid"/> <br><br>
	�� Ĩ:<jsp:getProperty name="player" property="userchip"/> <br><br>
		
	������̵�:<jsp:getProperty name="enemy" property="userid"/> <br><br>
	���� ī��:<jsp:getProperty name="enemy" property="usercard"/> <br><br>
	���� Ĩ:<jsp:getProperty name="enemy" property="userchip"/> <br><br>
	
	��밡 ������ Ĩ:<jsp:getProperty name="enemy" property="betchip"/> <br><br>
	
	<FORM METHOD=GET ACTiON="GameServer" >				<!-- submit ������ �̵��� ������ -->
	
	������ Ĩ: <INPUT TYPE="text" name="betchip" >	
			<INPUT TYPE="hidden" name="checkid" value="<%=player.getUserid()%>">	
	<input type="submit" value="����"></FORM><br><br>
	
	<FORM METHOD=GET ACTiON="GameServer" >	
	<INPUT TYPE="hidden" name="checkenemybet" value="kk">
	<INPUT TYPE="hidden" name="checkid" value="<%=player.getUserid()%>">	
	<input type="submit" value="��뺣��Ȯ��"></FORM>
	
	
	
	
</body>
</html>