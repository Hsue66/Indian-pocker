<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="KSC5601"%>

<jsp:useBean id="player" class="tom.PlayerInfo" scope="session"/>
<!-- <jsp:setProperty name="player" property="*"/> ���� 7���� ���� ���ʿ�
	 setProperty ���� param=""���� �� ���� -->

<% 
String userid= request.getParameter("userid");		// �Է¹��� �г����� string�� ����(18���ٿ� ���)
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<title>	 Login  </title>	
</head>
<body>
<%
	if(userid.isEmpty()==false) 					// �г����� �Է�������
	{
%>		<jsp:setProperty name="player" property="userid"/>	<!-- �г����� beanŬ������ ����-->
		<jsp:getProperty name="player" property="userid"/> �� ���ӿ� �����Ͻðڽ��ϱ�? <br><br>
		<a href ="connect.jsp"> ���ӹ� ���� </a><br>			<!-- ���ӹ����� ��ũ -->
		<a href ="login.html"> ó������	</a><br>			<!-- �α���ȭ�� ��ũ -->
<%	}
	else											// �г����� �Է����� �ʾ��� �� 
	{ 												// ���â ���
%>	<script type="text/javascript">alert("�г����� �Է��ϼ���!!");history.back();</script>
<%  }%>
</body>
</html>
