package tom;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**Servlet implementation class GameServer**/

public class GameServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// int userOrder[];				// player순서 array		인덱스0 => 1번플레이어
	int index = 0;					// 인덱스 변수
	
	String loginConfirm[];			// 로그인 확인 메시지를 저장할 배열
		
	public void init() {				// 초기화 함수
		
		//userOrder = new int[2];			// 2명의 순서를 정할 array 할당
		loginConfirm = new String[2];	// 총2명의 로그인 확인 메시지를 저장할 array 할당
		
		for(int i=0; i<2; i++) {
			//userOrder[i] = 0;			// int array는 0으로 초기화
			loginConfirm[i] = "";		// String array는 null로 초기화	
			
		}
	}
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("KSC5601");
		response.setContentType("text/html;charset=KSC5601");			// 인코딩 관련.....
		PrintWriter out = response.getWriter();
			
		String userid = request.getParameter("userid");			// user의 id를 받아와서
		if(userid!=null) {										// id가 null이아니라면 (당연히 아니겠지만..)
			synchronized (loginConfirm) {							// 한번에 한명씩
				loginConfirm[index] = userid +"님이 입장하셨습니다.";		// 로그인 확인 메시지 누적	
				if(loginConfirm[0].equals(loginConfirm[1])) {		// 새 유저의 닉네임이 겹치면
					loginConfirm[index] = "";						// 누적한 확인 메시지는 다시 지우고
					response.sendRedirect("loginError.jsp");		// 에러페이지로 보냄
					index=(index+1) % 2;							// 인덱스 변경..	
				}
				index=(index+1) % 2;							// 인덱스는 1을 넘지못함
			}
		}
			
		out.println("<html><head>");							// 2초마다 GameServer 새로고침
		out.println("<meta http-equiv='refresh'content = '2; url=GameServer'>");
		out.println("</head><body>");							
		
		out.println("<h3>드루와 드루와</h3>");
		for(int i=0; i<2; i++) out.println(loginConfirm[i]+"<br>");	// 모든 로그인 확인 메시지 출력
		
		
		
		out.println("</body></html>");
		out.close();
	}
}
