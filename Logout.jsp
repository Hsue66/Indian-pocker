<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
		
<% String count = (String)application.getAttribute("count");	// ���� �� ������ ��
	int n = Integer.parseInt(count);							// ������ �� = ������ �� -1
	n--;												
	application.setAttribute("count", Integer.toString(n));		// �� ������ �� �缳��
%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title> Bye bye~ </title>															<%--������� �α׾ƿ�--%>

</head> 
<body>

	<jsp:useBean id="nick" class="asd.Register" scope="session" />					<%--������� session scope ȣ��--%>		
	<jsp:getProperty name="nick" property="nickName" /> ���� ���������� �α׾ƿ��Ǿ����ϴ�.	<%--�α׾ƿ� �޽��� ���--%>
	
	<%session.invalidate(); %>														<%--���� ����(��ȿȭ)--%>
			
	<input type="button" value="ó������" onclick="location='Login.jsp'">				<%--�ʱ� �α��� ȭ������ �ǵ��ư�--%>
	
</body>
</html>