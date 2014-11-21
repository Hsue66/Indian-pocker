package tom;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**Servlet implementation class GameServer**/

public class GameServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static int userOrder;					// player 순서 제어 변수	
	String saveid[];						// player id를 저장할 배열
	int index;
	
	int y1, y2, y3, y4, y5;					// 그냥필요할때 가져다 쓰는 변수...
	
	int stage;
	int die;
	
	Dealer dealer = new Dealer();
	Players player1 = new Players();			
	Players player2 = new Players();
	
	public void init() {				// 초기화 함수
		player1.Init(1);				// 
		player2.Init(2);
		
		y1=0;		// 맘편히 가져다 쓰는 변수 초기화
		y2=0;		// 맘편히 가져다 쓰는 변수 초기화
		
		stage=0;
		die=0;
		index = 0;
		
		userOrder = 2;						// 2번째로그인하는 놈이 먼저 코드에 접근하므로... 
		saveid = new String[2];
		for(int i=0; i<2; i++) saveid[i] = "";
	}
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("KSC5601");
		response.setContentType("text/html;charset=KSC5601");			// 인코딩 관련.....
		PrintWriter out = response.getWriter();
	////////////////////////////////////////////////////////////////////////////////////////////////		
		
		String userid = request.getParameter("userid");			// user의 id를 받아와서
		if(userid!=null) {										// id가 null이아니라면 (당연히 아니겠지만..)
			synchronized (saveid) {							// 한번에 한명씩
				saveid[index] = userid;		
				index=(index+1) % 2;
			}
		}	
			
		if(saveid[1]!="") {				// 2명이 로그인했다면!!!!!!
				if(y1<1){				// 이부분은 한번만 반복되도록 제어..
						stage++;
						//System.out.printf("<%d라운드를 시작합니다>\n",stage);
						if(stage%10==1)	dealer.initCardDeck();			// 카드덱만들고
						dealer.distributeCard(player1, player2);		// 플레이어별로 카드뽑아주고
						dealer.initRound(player1, player2);				// 기본베팅을 해준다
						y1++;
				}
				
				if(y2<2) {				// 이부분은 2번만 반복되도록 제어..
					if(userOrder==1) {			
																		// 2번놈이 나가면 1번놈 차례
						request.setAttribute("mycard", Dealer.card1);
						request.setAttribute("enemycard", Dealer.card2);
						request.setAttribute("enemyid", saveid[1]);
						request.setAttribute("myleftchip", player1.getChips());
						request.setAttribute("enemyleftchip", player2.getChips());		// 정보들고 1번플레이어도 jsp로 이동
						request.getRequestDispatcher("playeroutput.jsp").forward(request, response);
						y2++;			// 더이상 이 코드엔 안들어올것임.. 아마 2번째 라운드 때 다시 올수도...
						
					}
					else if(userOrder==2) {					// %%%현재순서는2이므로 여기부터 시작%%%%
						request.setAttribute("mycard", Dealer.card2);				// 나=player2 이니까 card2)
						request.setAttribute("enemycard", Dealer.card1);			// 상대방 카드
						request.setAttribute("enemyid", saveid[0]);					// 상대방 id
						request.setAttribute("myleftchip", player2.getChips());		// 내 남은 칩수
						request.setAttribute("enemyleftchip", player1.getChips());	// 상대방 남은 칩수
						request.getRequestDispatcher("playeroutput.jsp").forward(request, response);// 위 정보를 들고  플레이어2는 jsp로 이동
						userOrder--;		// 순서 변경
						
					}
				}
				
		}
		
		String temp = request.getParameter("betchip");			// jsp에서 베팅한 값 저장
		String temp2 = request.getParameter("checkid");			// 사용자 구분을 위해....
		String temp3 = request.getParameter("checkenemybet");	// 상대방 베팅정보 불러올때 사용...
	
		
		if(temp3!=null) {				// '상대베팅확인'을 누르고 왔다면 !!
			if(userOrder==2 && temp2.equals(saveid[1])) {		// 현재순서는 2번 플레이어이고 그놈 id까지 맞다면
				request.setAttribute("myleftchip", player2.getChips());	// 나의 남은칩과
				request.setAttribute("enemyleftchip", player1.getChips());	// 상대방의 남은칩과
				request.setAttribute("enemybetchip", player1.getBetchip());	// 상대방이 베팅한 칩 정보를 들고
				request.getRequestDispatcher("playeroutput.jsp").forward(request, response);	// 다시 jsp로 이동
			}
			////***************************************************************************////
			else {		// 그 다음 사람
				request.setAttribute("myleftchip", player1.getChips());	// 나의 남은칩과
				request.setAttribute("enemyleftchip", player2.getChips());	// 상대방의 남은칩과
				request.setAttribute("enemybetchip", player2.getBetchip());	// 상대방이 베팅한 칩 정보를 들고
				request.getRequestDispatcher("playeroutput.jsp").forward(request, response);	// 다시 jsp로 이동
			}
			////***************************************************************************////
		}
		
		if(temp!=null && temp2!=null)	// 베팅을 했다면
		{	
			if(userOrder==1 && temp2.equals(saveid[0]))		// 1번 순서고 id까지 맞으면
			{	
				player1.setBetchip(temp);					// 베팅한 값을 int로 변환
				int checkbetting = player1.betChips();			// 베팅 가능 여부를 확인 (betChips함수 참고)
				if(checkbetting==1) {							
					out.println("<script type='text/javascript'>"
						+ "alert('가진 칩 수보다  많습니다. 다시 입력해주세요');history.back();</script>");
				}
				else if(checkbetting==2) {
					out.println("<script type='text/javascript'>"
						+ "alert('0개를 배팅하실 수 없습니다. 다시 입력해주세요');history.back();</script>");
				}
				else if(checkbetting==3) {
					System.out.printf("플레이어가 베팅한 칩 %d\n", player1.getBetchip());
					System.out.printf("플레이어의 남은칩 %d\n", player1.getChips());
					System.out.printf("누적 칩 %d\n", player1.getAccchip());
					userOrder++;					// 1번플레이어가 제대로 베팅하면 1번플레이어는 서블릿에서 멈추고 순서 넘김
					
				}
				
			}
			else if(userOrder==2 && temp2.equals(saveid[1]))	// 2번 순서고 id까지 맞으면
			{	
				player2.setBetchip(temp);
				int checkbetting = player2.betChips();
				if(checkbetting==1) {
					out.println("<script type='text/javascript'>"
						+ "alert('가진 칩 수보다  많습니다. 다시 입력해주세요');history.back();</script>");
				}
				else if(checkbetting==2) {
					out.println("<script type='text/javascript'>"
						+ "alert('0개를 배팅하실 수 없습니다. 다시 입력해주세요');history.back();</script>");
				}
				else if(checkbetting==3) {
					System.out.printf("플레이어가 베팅한 칩 %d\n", player2.getBetchip());
					System.out.printf("플레이어의 남은칩 %d\n", player2.getChips());
					System.out.printf("누적 칩 %d\n", player2.getAccchip());
					//userOrder++;				// 2번 플레이어도 서블릿에서 멈추고  &&&&&&&&& 일단 여기서 끝!!&&&&&&
					request.getRequestDispatcher("playeroutput.jsp").forward(request, response);	// 다시 jsp로 이동
				}
			}
			else out.println("<script type='text/javascript'>"	// 본인 차례가 아닐떄
					+ "alert('당신차례가아냐');history.back();</script>");
			}
		
		
		out.println("1번 플레이어가 베팅한 칩: "+player1.getBetchip()+"<br>");
		out.println("1번 플레이어의 남은칩: "+player1.getChips()+"<br>");
		out.println("1번 플레이어의 누적 칩 : "+player1.getAccchip()+"<br>");
		out.println("2번 플레이어가 베팅한 칩 : "+player2.getBetchip()+"<br>");
		out.println("2번 플레이어의 남은칩 : "+player2.getChips()+"<br>");
		out.println("2번 플레이어의 누적 칩 : "+player2.getAccchip()+"<br>");
		
			
				
	out.println("<html><head>");							// 2초마다 GameServer 새로고침
	out.println("<meta http-equiv='refresh'content = '10; url=GameServer'>");
	out.println("</head><body>");							
	out.println("<h3>드루와 드루와</h3>");
	for(int i=0; i<2; i++) out.println(saveid[i]+"<br>");	// 모든 로그인 확인 메시지 출력
	out.println("</head><body>");
	out.println("</body></html>");
	
	
	

	   
					
				/*while(true)
				{
					if(dealer.EndorNot()==0)
						dealer.betting(player1, player2);			
					else
						break;
					die=dealer.checkDie(player1,player2);
					
					if(die==1)
						break;
					
					System.out.println();
					if(dealer.EndorNot()==0)
						dealer.betting(player2, player1);
					else
						break;
					die=dealer.checkDie(player1,player2);
					
					if(die==1)
						break;
				}*/
				
		
////////////////////////////////////////////////////////////////////////////////////////////////	
		
		

		out.close();	
		}
}
