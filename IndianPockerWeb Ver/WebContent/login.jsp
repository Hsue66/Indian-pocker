<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="KSC5601"%>

<jsp:useBean id="player" class="tom.PlayerInfo" scope="session"/>
<!-- <jsp:setProperty name="player" property="*"/> ���� 8���� ���� ���ʿ�
	 setProperty ���� param=""���� �� ���� -->

<% 
String userid= request.getParameter("userid");		// �Է¹��� �г����� string�� ����(18���ٿ� ���)
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>	 Login  </title>	
</head>
<body>
<%
	if(userid.isEmpty()==false) 					// �г����� �Է�������
	{
%>		<jsp:setProperty name="player" property="userid"/>	<!-- �г����� beanŬ������ ����-->
		<jsp:getProperty name="player" property="userid"/> �� ���ӿ� �����Ͻðڽ��ϱ�? <br><br>
		<FORM METHOD=GET ACTiON="GameServer" >	
		<INPUT TYPE="hidden" name="userid" value="<%=player.getUserid()%>">			<!--  id�� gameserver�� �Ѱ��� -->
		<input type="submit" value="���ӹ� ����">				
		</FORM>
		<FORM METHOD=GET ACTiON="login.html" >	
		<input type="submit" value="�α���â����">				
		</FORM>
		
<%	}
	else											// �г����� �Է����� �ʾ��� �� 
	{ 												// ���â ���
%>	<script type="text/javascript">alert("�г����� �Է��ϼ���!!");history.back();</script>
<%  }%>
</body>
</html>
