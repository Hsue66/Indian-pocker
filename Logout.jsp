<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
		
<% String count = (String)application.getAttribute("count");	// 현재 총 접속자 수
	int n = Integer.parseInt(count);							// 접속자 수 = 접속자 수 -1
	n--;												
	application.setAttribute("count", Integer.toString(n));		// 총 접속자 수 재설정
%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title> Bye bye~ </title>															<%--사용자의 로그아웃--%>

</head> 
<body>

	<jsp:useBean id="nick" class="asd.Register" scope="session" />					<%--사용자의 session scope 호출--%>		
	<jsp:getProperty name="nick" property="nickName" /> 님이 정상적으로 로그아웃되었습니다.	<%--로그아웃 메시지 출력--%>
	
	<%session.invalidate(); %>														<%--세션 삭제(무효화)--%>
			
	<input type="button" value="처음으로" onclick="location='Login.jsp'">				<%--초기 로그인 화면으로 되돌아감--%>
	
</body>
</html>