<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<jsp:useBean id="player" class="tom.PlayerInfo" scope="session"/>
<jsp:useBean id="enemy" class="tom.PlayerInfo" scope="session"/>
<%
	// 출력할 메시지들  //
	String msg = (String)request.getAttribute("msg");
	String losemsg = (String)request.getAttribute("losemsg");
	String winmsg = (String)request.getAttribute("winmsg");
	String penalty = (String)request.getAttribute("penalty");
	String open = (String)request.getAttribute("open");

	// 게임 종료 //
	String gameEndFlag = (String)request.getAttribute("gameEndFlag");
	
	// 내 정보  //
	Integer mycard = (Integer)request.getAttribute("mycard");
	Integer myleftchip = (Integer)request.getAttribute("myleftchip");
	Integer mybetchip = (Integer)request.getAttribute("mybetchip");
	Integer myaccchips = (Integer)request.getAttribute("myaccchips");
	
	// 상대 정보  //
	String enemyid = (String)request.getAttribute("enemyid");
	Integer enemycard = (Integer)request.getAttribute("enemycard");
	Integer enemyleftchip = (Integer)request.getAttribute("enemyleftchip");
	Integer enemybetchip = (Integer)request.getAttribute("enemybetchip");
	Integer enemyaccchips = (Integer)request.getAttribute("enemyaccchips");
	
	// 현재 플레이어 //
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
				<!-- 내 아이디 출력 부분 -->
				<div class = "my_id" align="center">	
					<h2><jsp:getProperty name="player" property="userid"/></h2> 
				</div>		
			
				<div class = "header" align="center" >
					<h1></h1>
				</div>	
			
				<!-- 상대 아이디 출력 부분 -->
				<div class = "other_id" align="center">
					<h2><jsp:getProperty name="enemy" property="userid"/></h2> 
				</div>
			</div>	
				
			<!-- 내 카드 출력 부분 -->
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
			
			<!-- 내 칩 출력 부분 -->
			<div class = "my_info" align="center">
				<h1><jsp:getProperty name="player" property="userchip"/></h1>
			</div>

			<!-- 상태창 출력 부분 -->
			<div class="state" align="center">
				<%if(nowplayer!=null){ %>
				<jsp:getProperty name="player" property="userorder"/> 차례입니다.<br>
				<%} if(enemybetchip!=null) {%>
				상대가 베팅한 칩:<jsp:getProperty name="enemy" property="betchip" /><br>
				<%} if(myaccchips!=null){ %>
				내누적:<jsp:getProperty name="player" property="useraccchips" /> 
				<%} if(enemyaccchips!=null){ %>
				상대누적:<jsp:getProperty name="enemy" property="useraccchips" />
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

			<!-- 배팅창 부분 -->
			<div class="bet" align="center">
			<!-- 배팅 폼 -->
			<% if(gameEndFlag == null || gameEndFlag.equals("false"))	// gameEndFlag의값이 true가아니면 배팅창
			{%>
				<% if(open==null) { %>
				<!-- submit 누를시 이동할 페이지 -->
				<FORM METHOD=GET ACTiON="GameServer">
					베팅할 칩: <INPUT TYPE="text" name="betchip"> 
					<INPUT TYPE="hidden" name="checkid" value="<%=player.getUserid()%>">
					<input type="submit" value="베팅">
				</FORM>
				<%}else if(open.equals("open"));
			   	else {%>
				<FORM METHOD=GET ACTiON="GameServer">
					베팅할 칩: <INPUT TYPE="text" name="betchip"> 
					<INPUT TYPE="hidden" name="checkid" value="<%=player.getUserid()%>">
					<input type="submit" value="베팅">
				</FORM>
				<%}%>
				<!-- 배팅확인 폼 -->
				<FORM METHOD=GET ACTiON="GameServer">
					<INPUT TYPE="hidden" name="checkenemybet" value="kk"> 
					<INPUT TYPE="hidden" name="checkid" value="<%=player.getUserid()%>">
					<input type="submit" value="상대베팅확인">
				</FORM>
				<%if(open==null){}		
				else if(open.equals("open")){ %>
				<BR>
				<FORM METHOD=GET ACTiON="GameServer">
					<INPUT TYPE="hidden" name="nextround" value="kk"> 
					<INPUT TYPE="hidden" name="checkid" value="<%=player.getUserid()%>">
					<input type="submit" value="다음라운드">
				</FORM>
				<%}%>
			<%} else if(gameEndFlag.equals("true")){%>				<!--  gameEndFlag의값이 true일때 배팅창을 없엔다. -->	
				<FORM METHOD=GET ACTION="login.html">
					<input type="submit" value="초기화면으로">
				</FORM>			
			<%}%>

		</div>
	
			<% String card2="./img/"+enemycard+".png";%>
			<!-- 상대 카드 출력 부분 -->
			<div class="other_card" align="center" >
				<img src="<%=card2 %>" style="position:relative; left:4px; top:20px;"
				 width="180px" height="270px">
			</div>
			
			<!-- 상대 칩 출력 부분 -->
			<div class = "other_info" align="center">
					<h1><jsp:getProperty name="enemy" property="userchip"/></h1>
			</div>
			
		</div>
	</body>
</html>
