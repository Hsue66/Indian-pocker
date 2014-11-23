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
	
	// 상태 메세지 띠워주는 변수들  //
	String msg;
	String winmsg;	
	String losemsg;
	String penalty;
	String open;
	
	int y1, y2, y3, y4, y5;					// 그냥필요할때 가져다 쓰는 변수...
	
	int stage;
	int die;
	
	// Dealer 객체와 Player 객체 생성  // 
	Dealer dealer = new Dealer();
	Players player1 = new Players();			
	Players player2 = new Players();
	
	
	//***************************************초기화 함수***************************************//
	public void init() 
	{
		player1.Init(1);	//Player 초기화			
		player2.Init(2);
		
		y1=0;		// 맘편히 가져다 쓰는 변수 초기화
		y2=0;		// 맘편히 가져다 쓰는 변수 초기화
		
		stage=0;
		die=0;
		index = 0;
		
		// 메시지 초기화
		msg="";
		losemsg="";
		winmsg="";
		penalty="";
		open="";
		
		userOrder = 2;						// 2번째로그인하는 놈이 먼저 코드에 접근하므로... 
		saveid = new String[2];
		for(int i=0; i<2; i++) saveid[i] = "";
	}
	
	
	//***************************************상태 출력전용 함수***************************************//
	public void showThings(HttpServletRequest request, HttpServletResponse response,int flag) 
			throws ServletException, IOException
	{
		if(flag==0)			// player1에서 출력하는 경우
		{
			request.setAttribute("myleftchip", player1.getChips());		// 나의 남은칩과
			request.setAttribute("enemyleftchip", player2.getChips());	// 상대방의 남은칩과
			request.setAttribute("enemybetchip", player2.getBetchip());	// 상대방이 베팅한 칩 정보를 들고
			request.setAttribute("myaccchips", player1.getAccchip());	// 나의 누적칩
			request.setAttribute("enemyaccchips", player2.getAccchip());// 상대 누적칩
			
			request.setAttribute("nowplayer", userOrder);				// 현재 배팅 순서
			request.setAttribute("msg", msg);							// 상태 메세지
			request.setAttribute("losemsg", losemsg);					// 진 메시지
			request.setAttribute("winmsg", winmsg);						// 이긴 메시지
			request.setAttribute("penalty", penalty);					// 페널티 메시지
			request.setAttribute("open", open);							// 카드 공개 
			
			request.getRequestDispatcher("playeroutput.jsp").forward(request, response);	// 다시 jsp로 이동
		}
		else				// player2에서 출력하는 경우
		{
			request.setAttribute("myleftchip", player2.getChips());		// 나의 남은칩과
			request.setAttribute("enemyleftchip", player1.getChips());	// 상대방의 남은칩과
			request.setAttribute("enemybetchip", player1.getBetchip());	// 상대방이 베팅한 칩 정보를 들고
			request.setAttribute("myaccchips", player2.getAccchip());	// 나의 누적칩
			request.setAttribute("enemyaccchips", player1.getAccchip());// 상대 누적칩
			
			request.setAttribute("nowplayer", userOrder);				// 현재 배팅 순서
			request.setAttribute("msg", msg);							// 상태 메세지
			request.setAttribute("losemsg", losemsg);					// 진 메시지
			request.setAttribute("winmsg", winmsg);						// 이긴 메시지
			request.setAttribute("penalty", penalty);					// 페널티 메시지
			request.setAttribute("open", open);							// 카드 공개 
			
			request.getRequestDispatcher("playeroutput.jsp").forward(request, response);	// 다시 jsp로 이동
		}
	}

	//***************************************한 라운드 결과 함수***************************************//
	public void RoundResult(int nowP)
	{
		System.out.println(nowP);
		
		// 현재 플레이어가 포기했을 경우  //
		if(player1.getLose()!=0 || player2.getLose()!=0)
		{
			if(nowP==1)			// 1 플레이어인 경우
			{
				dealer.checkRound(player1,player2,Dealer.card1);	// 딜러가 라운드 체크
				
				if(Dealer.card1 == 10)	// 포기한 플레이어의 카드가 10인 경우 
					penalty = nowP + " 플레이어가10카드이므로 패널티가 적용됩니다.";
				
				System.out.println(player1.getChips());
				System.out.println(player2.getChips());
			}
			else			// 2 플레이어인 경우
			{
				dealer.checkRound(player2,player1,Dealer.card2);	// 딜러가 라운드 체크
				
				if(Dealer.card2 == 10)	// 포기한 플레이어의 카드가 10인 경우 
					penalty = nowP + " 플레이어가10카드이므로 패널티가 적용됩니다.";
				
				System.out.println(player1.getChips());
				System.out.println(player2.getChips());
			}
			open="open";	// 카드 오픈 메세지
		}
		
		// 같은 칩 배팅한 경우  //
		else
		{
			dealer.checkSamechipRound(player1, player2);	// 딜러가 라운드 체크 (같은 칩 배팅인 경우)

			if(Dealer.card1 > Dealer.card2)		// 플레이어 1의 카드가 큰 경우
			{
				losemsg = "2 플레이어가 패하였습니다.";
				winmsg = "1 플레이어가 승리하였습니다.";
			}
			else if(Dealer.card1 < Dealer.card2)// 플레이어 2의 카드가 큰 경우
			{
				losemsg = "1 플레이어가 패하였습니다.";
				winmsg = "2 플레이어가 승리하였습니다.";	
			}
			else								// 카드가 같은 경우
				winmsg= "무승부입니다.";
			
			open="open";	// 카드 오픈 메세지
		}
	}

	
	//***************************************게임 진행 함수***************************************//
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		request.setCharacterEncoding("KSC5601");
		response.setContentType("text/html;charset=KSC5601");			// 인코딩 관련.....
		PrintWriter out = response.getWriter();
		
		String userid = request.getParameter("userid");			// user의 id를 받아와서
		if(userid!=null) {										// id가 null이아니라면 (당연히 아니겠지만..)
			synchronized (saveid) {							// 한번에 한명씩
				saveid[index] = userid;		
				index=(index+1) % 2;
			}
		}	
		
		if(saveid[1]!="") 
		{				// 2명이 로그인했다면!!!!!!
			if(y1<1)
			{				// 이부분은 한번만 반복되도록 제어..
					stage++;
					//System.out.printf("<%d라운드를 시작합니다>\n",stage);
					if(stage%10==1)	dealer.initCardDeck();			// 카드덱만들고
					dealer.distributeCard(player1, player2);		// 플레이어별로 카드뽑아주고
					dealer.initRound(player1, player2);				// 기본베팅을 해준다
					y1++;
			}
			
			if(y2<2) 
			{				// 이부분은 2번만 반복되도록 제어..
				if(userOrder==1) 
				{			
																	// 2번놈이 나가면 1번놈 차례
					request.setAttribute("mycard", Dealer.card1);
					request.setAttribute("enemycard", Dealer.card2);
					request.setAttribute("enemyid", saveid[1]);
					request.setAttribute("myleftchip", player1.getChips());
					request.setAttribute("enemyleftchip", player2.getChips());		// 정보들고 1번플레이어도 jsp로 이동
					request.getRequestDispatcher("playeroutput.jsp").forward(request, response);
					y2++;			// 더이상 이 코드엔 안들어올것임.. 아마 2번째 라운드 때 다시 올수도...	
				}
				else if(userOrder==2) 
				{					// %%%현재순서는2이므로 여기부터 시작%%%%
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
	
		
		// '상대베팅확인'을 누르고 온 경우 //
		if(temp3!=null) 
		{											
			if(userOrder==1 && temp2.equals(saveid[0])) 			// 내가 1번인데  내순서인경우
				showThings(request,response,0);
			
			else if(userOrder==2 && temp2.equals(saveid[0])) 		// 내가 1번인데  상대순서인경우
				showThings(request,response,0);
			
			else if(userOrder==2 && temp2.equals(saveid[1]))		// 내가 2번인데  내순서인경우
				showThings(request,response,1);
			
			else if(userOrder==1 && temp2.equals(saveid[1]))		// 내가 2번인데  상대순서인경우
				showThings(request,response,1);
		}

		// 배팅을 한 경우 //
		if(temp2!=null)
		{	
			if(temp1!=null)
			{
				if(userOrder==1 && temp2.equals(saveid[0]))		// 1번 순서고 id까지 맞으면
				{	
					player1.setBetchip(temp);					// 베팅한 값을 int로 변환
					int checkbetting = player1.betChips();		// 베팅 가능 여부를 확인 (betChips함수 참고)
				
					// 잘못배팅한 경우 - 딜러가 체크해주는 경우  //
					if(player1.getAccchip()<player2.getAccchip() && checkbetting!=1 && checkbetting!=2)	
					{
						out.println("<script type='text/javascript'>"
						+ "alert('상대방의 누적된 칩수와 같거나 크게 배팅해야합니다.\n 다시 입력해주세요');history.back();</script>");
						player1.wrongBetchips(); 				// 잘못 배팅했으므로 칩 원상복귀
					}
				
					else // -스스로 바보같이 배팅했는지 체크
					{
						if(checkbetting==1)				// 가진 것보다 많이 배팅한 경우 
						{
							out.println("<script type='text/javascript'>"
							+ "alert('가진 칩 수보다  많습니다.\n 다시 입력해주세요');history.back();</script>");
						}
						else if(checkbetting==2) 		// 배팅 포기한 경우 
						{
							player1.changeLose(0);						// 라운드 포기 표시하기
							RoundResult(userOrder);						// 라운드 결과 확인 함수	
						
							losemsg = userOrder +" 플레이어가 포기하였습니다.";
							winmsg = userOrder+1 +" 플레이어가 승리하였습니다.";
							showThings(request,response,0);				// 상태 메세지들 출력
						}
						else if(checkbetting==3) 		// 배팅한 경우 
						{
							System.out.printf("1 플레이어가 베팅한 칩 %d\n", player1.getBetchip());
							System.out.printf("1 플레이어의 남은칩 %d\n", player1.getChips());
							System.out.printf("1 누적 칩 %d\n", player1.getAccchip());
						
							userOrder++;	// 플레이어 순서 변경
						
							if(dealer.checkSame(player1, player2)!=0)	// 배팅된 칩이 같은 개수인 경우
							{
								RoundResult(userOrder);					// 라운드 결과 확인 함수
								msg = "같은수를 배팅하셨습니다.";
							}
							showThings(request,response,0);				// 상태 메세지들 출력
						}
					}
				}
			
				else if(userOrder==2 && temp2.equals(saveid[1]))	// 2번 순서고 id까지 맞으면
				{	
					player2.setBetchip(temp);						// 베팅한 값을 int로 변환
					int checkbetting = player2.betChips();			// 베팅 가능 여부를 확인 (betChips함수 참고)
				
					// 잘못배팅한 경우 - 딜러가 체크해주는 경우  //
					if(player2.getAccchip()<player1.getAccchip() && checkbetting!=1 && checkbetting!=2)
					{
						out.println("<script type='text/javascript'>"
						+ "alert('상대방의 누적된 칩수와 같거나 크게 배팅해야합니다.\n 다시 입력해주세요');history.back();</script>");
						player2.wrongBetchips(); 				// 잘못 배팅했으므로 칩 원상복귀
					}
				
					else // -스스로 바보같이 배팅안했는지 체크
					{
						if(checkbetting==1) 			// 가진 것보다 많이 배팅한 경우 
						{
							out.println("<script type='text/javascript'>"
							+ "alert('가진 칩 수보다  많습니다.\n 다시 입력해주세요');history.back();</script>");
						}
						else if(checkbetting==2) 		// 배팅 포기한 경우 
						{
							player2.changeLose(0);						// 라운드 포기 표시하기
							RoundResult(userOrder);						// 라운드 결과 확인 함수
						
							losemsg = userOrder+" 플레이어가 포기하였습니다.";
							winmsg = userOrder-1 +" 플레이어가 승리하였습니다.";
							showThings(request,response,0);				// 상태 메세지들 출력
						}
						else if(checkbetting==3) 		// 배팅한 경우  
						{
							System.out.printf("2 플레이어가 베팅한 칩 %d\n", player2.getBetchip());
							System.out.printf("2 플레이어의 남은칩 %d\n", player2.getChips());
							System.out.printf("2 누적 칩 %d\n", player2.getAccchip());
						
							userOrder--;	// 플레이어 순서 변경
						
							if(dealer.checkSame(player1, player2)!=0)	// 배팅된 칩이 같은 개수인 경우
							{
								RoundResult(userOrder);					// 라운드 결과 확인 함수
								msg = "같은수를 배팅하셨습니다.";
							}
							showThings(request,response,1);				// 상태 메세지들 출력
						}
					}
				}
				else out.println("<script type='text/javascript'>"	// 본인 차례가 아닐떄	
					+ "alert('당신차례가아냐');history.back();</script>");
			}
			else out.println("<script type='text/javascript'>"	// 입력값 없이 베팅할 때	
					+ "alert('입력값이 없습니다');history.back();</script>");
		}

				
	out.println("<html><head>");							// 3초마다 GameServer 새로고침
	out.println("<meta http-equiv='refresh'content = '3; url=GameServer'>");
	out.println("</head><body>");							
	out.println("<h3>드루와 드루와</h3>");
	for(int i=0; i<2; i++) out.println(saveid[i]+"<br>");	// 모든 로그인 확인 메시지 출력
	out.println("</head><body>");
	out.println("</body></html>");
	out.close();	

	}
}
