<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<jsp:useBean id="player" class="tom.PlayerInfo" scope="session"/>
<jsp:useBean id="enemy" class="tom.PlayerInfo" scope="session"/>
<%
	// ����� �޽�����  //
	String msg = (String)request.getAttribute("msg");
	String losemsg = (String)request.getAttribute("losemsg");
	String winmsg = (String)request.getAttribute("winmsg");
	String penalty = (String)request.getAttribute("penalty");
	String open = (String)request.getAttribute("open");

	// ���� ���� //
	String gameEndFlag = (String)request.getAttribute("gameEndFlag");
	
	// �� ����  //
	Integer mycard = (Integer)request.getAttribute("mycard");
	Integer myleftchip = (Integer)request.getAttribute("myleftchip");
	Integer mybetchip = (Integer)request.getAttribute("mybetchip");
	Integer myaccchips = (Integer)request.getAttribute("myaccchips");
	
	// ��� ����  //
	String enemyid = (String)request.getAttribute("enemyid");
	Integer enemycard = (Integer)request.getAttribute("enemycard");
	Integer enemyleftchip = (Integer)request.getAttribute("enemyleftchip");
	Integer enemybetchip = (Integer)request.getAttribute("enemybetchip");
	Integer enemyaccchips = (Integer)request.getAttribute("enemyaccchips");
	
	// ���� �÷��̾� //
	Integer nowplayer = (Integer)request.getAttribute("nowplayer");

	String card="./img/"+mycard+".png";
	
	if(mycard!=null)%><jsp:setProperty name="player" property="usercard" value="<%=mycard%>"/>	
<%	if(myleftchip!=null)%><jsp:setProperty name="player" property="userchip" value="<%=myleftchip%>"/>
<%	if(mybetchip!=null)%><jsp:setProperty name="player" property="betchip" value="<%=mybetchip%>"/>
<%	if(myaccchips!=null)%><jsp:setProperty name="player" property="useraccchips" value="<%=myaccchips%>"/>

<%	if(enemyid!=null)%><jsp:setProperty name="enemy" property="userid" value="<%=enemyid%>"/>
<%	if(enemycard!=null)%><jsp:setProperty name="enemy" property="usercard" value="<%=enemycard%>"/>	
<%	if(enemyleftchip!=null)%><jsp:setProperty name="enemy" property="userchip" value="<%=enemyleftchip%>"/>
<%	if(enemybetchip!=null)%><jsp:setProperty name="enemy" property="betchip" value="<%=enemybetchip%>"/>
<%	if(enemyaccchips!=null)%><jsp:setProperty name="enemy" property="useraccchips" value="<%=enemyaccchips%>"/>

<%	if(nowplayer!=null)%><jsp:setProperty name="player" property="userorder" value="<%=nowplayer%>"/>	
	

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
		<title>INDIAN POCKER</title>
			<link rel="stylesheet" href="style.css"/>
	</head>
	
	<body>
		<div class = "container">

			<div class= " head">
				<!-- �� ���̵� ��� �κ� -->
				<div class = "my_id" align="center">	
					<h2><jsp:getProperty name="player" property="userid"/></h2> 
				</div>		
			
				<div class = "header" align="center" >
					<h1></h1>
				</div>	
			
				<!-- ��� ���̵� ��� �κ� -->
				<div class = "other_id" align="center">
					<h2><jsp:getProperty name="enemy" property="userid"/></h2> 
				</div>
			</div>	
				
			<!-- �� ī�� ��� �κ� -->
			<div class = "my_card" align="center">
			<%if(open==null) { %>
				<img src="./img/bcard.png" style="position:relative; left:4px; top:20px;"
				 width="180px" height="270px">
				<%}else if(open.equals("open")){ 
					
					%>
				<img src="<%=card %>" style="position:relative; left:4px; top:20px;"
				 width="180px" height="270px">

				<%} else{%>
				<img src="./img/bcard.png" style="position:relative; left:4px; top:20px;"
				 width="180px" height="270px">
				<%} %>
			</div>
			
			<!-- �� Ĩ ��� �κ� -->
			<div class = "my_info" align="center">
				<h1><jsp:getProperty name="player" property="userchip"/></h1>
			</div>

			<!-- ����â ��� �κ� -->
			<div class="state" align="center">
				<%if(nowplayer!=null){ %>
				<jsp:getProperty name="player" property="userorder"/> �����Դϴ�.<br>
				<%} if(enemybetchip!=null) {%>
				��밡 ������ Ĩ:<jsp:getProperty name="enemy" property="betchip" /><br>
				<%} if(myaccchips!=null){ %>
				������:<jsp:getProperty name="player" property="useraccchips" /> 
				<%} if(enemyaccchips!=null){ %>
				��봩��:<jsp:getProperty name="enemy" property="useraccchips" />
				<%} if(msg!=null){ %>
				<br><%=msg %><br>
				<%} if(losemsg!=null){ %>
				<%=losemsg%><br>
				<%} if(winmsg!=null){ %>
				<%=winmsg %><br>
				<%} if(penalty!=null){ %>
				<%=penalty %>
				<%} %>
			</div>

			<!-- ����â �κ� -->
			<div class="bet" align="center">
			<!-- ���� �� -->
			<% if(gameEndFlag == null || gameEndFlag.equals("false"))	// gameEndFlag�ǰ��� true���ƴϸ� ����â
			{%>
				<% if(open==null) { %>
				<!-- submit ������ �̵��� ������ -->
				<FORM METHOD=GET ACTiON="GameServer">
					������ Ĩ: <INPUT TYPE="text" name="betchip"> 
					<INPUT TYPE="hidden" name="checkid" value="<%=player.getUserid()%>">
					<input type="submit" value="����">
				</FORM>
				<%}else if(open.equals("open"));
			   	else {%>
				<FORM METHOD=GET ACTiON="GameServer">
					������ Ĩ: <INPUT TYPE="text" name="betchip"> 
					<INPUT TYPE="hidden" name="checkid" value="<%=player.getUserid()%>">
					<input type="submit" value="����">
				</FORM>
				<%}%>
				<!-- ����Ȯ�� �� -->
				<FORM METHOD=GET ACTiON="GameServer">
					<INPUT TYPE="hidden" name="checkenemybet" value="kk"> 
					<INPUT TYPE="hidden" name="checkid" value="<%=player.getUserid()%>">
					<input type="submit" value="��뺣��Ȯ��">
				</FORM>
				<%if(open==null){}		
				else if(open.equals("open")){ %>
				<BR>
				<FORM METHOD=GET ACTiON="GameServer">
					<INPUT TYPE="hidden" name="nextround" value="kk"> 
					<INPUT TYPE="hidden" name="checkid" value="<%=player.getUserid()%>">
					<input type="submit" value="��������">
				</FORM>
				<%}%>
			<%} else if(gameEndFlag.equals("true")){%>				<!--  gameEndFlag�ǰ��� true�϶� ����â�� ������. -->	
				<FORM METHOD=GET ACTION="login.html">
					<input type="submit" value="�ʱ�ȭ������">
				</FORM>			
			<%}%>

		</div>
	
			<% String card2="./img/"+enemycard+".png";%>
			<!-- ��� ī�� ��� �κ� -->
			<div class="other_card" align="center" >
				<img src="<%=card2 %>" style="position:relative; left:4px; top:20px;"
				 width="180px" height="270px">
			</div>
			
			<!-- ��� Ĩ ��� �κ� -->
			<div class = "other_info" align="center">
					<h1><jsp:getProperty name="enemy" property="userchip"/></h1>
			</div>
			
		</div>
	</body>
</html>
