<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
 int n = 0;
 
 String count = (String)application.getAttribute("count");
 
 if(count != null)
  n = Integer.parseInt(count);
 
 n++;
 application.setAttribute("count", Integer.toString(n));

 // 웹 서버에서 확인할 수 있는 실제 접속자의 주소(consol 창)
 application.log("접속자 : " + request.getRemoteAddr());
%>

<jsp:useBean id="nick" class="login.Register" scope="page" />
<jsp:setProperty name="nick" property="*"/>
<%
	boolean result = nick.checkNickName();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>대기화면</title>
</head>
<body>
<%
	if(result)
	{%>
		<form name="nick" method="post" action="Login.jsp">
		<jsp:getProperty name="nick" property="nickName" />
		님이 접속하셨습니다.
		</form>	
	<%}else{
		%><script type="text/javascript">alert("닉네임을 입력해주세요.");history.back();</script>
	<%}
 %>

총 접속자 : <%=n %><br>
</body>
</html>