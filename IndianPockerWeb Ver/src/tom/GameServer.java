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
	
	String gameEndFlag;	// 게임 종료 flag
	
	/* 진행 변수들 선언 */
	int roundFlag;				
	int playTurn;
	int loop;
	int gameclosed;
	int stage;
	int die;
	int rejoin;
	int refreshcount;			// 상대베팅확인 횟수
		
	// Dealer 객체와 Player 객체 생성  // 
	Dealer dealer = new Dealer();
	Players player1 = new Players();			
	Players player2 = new Players();
	
	//***************************************초기화 함수***************************************//
	public void init() 
	{
		player1.Init(1);	//Player 초기화			
		player2.Init(2);
		
		stage=0;
		die=0;
		index = 0;
		rejoin=0;
		refreshcount=0;
		
		roundFlag=0;		// 라운드 시작을 구분하는 flag
		playTurn=0;			// player의 순서를 결정하는 flag
		loop=0;				// 게임을 반복하는 변수
		gameEndFlag="false"; // false 면 게임진행중 true면 게임 종료
		messageInit();
		
		userOrder = 2;						// 2번째로그인하는 놈이 먼저 코드에 접근하므로... 
		saveid = new String[2];
		for(int i=0; i<2; i++) saveid[i] = "";
	}
	
	public void messageInit()
	{
		// 메시지 초기화
		msg="";
		losemsg="";
		winmsg="";
		penalty="";
		open="";
	}
	//***************************************상태 출력전용 함수***************************************//
	public void showThings(HttpServletRequest request, HttpServletResponse response,int flag) 
			throws ServletException, IOException
	{
		if(flag==0)			// player1에서 출력하는 경우
		{
			request.setAttribute("mycard", Dealer.card1);				// 각종 정보들을 가지고
			request.setAttribute("enemycard", Dealer.card2);
			
			request.setAttribute("myleftchip", player1.getChips());		// 나의 남은칩과
			request.setAttribute("enemyleftchip", player2.getChips());	// 상대방의 남은칩과
			request.setAttribute("enemybetchip", player2.getBetchip());	// 상대방이 베팅한 칩 정보를 들고
			request.setAttribute("myaccchips", player1.getAccchip());	// 나의 누적칩
			request.setAttribute("enemyaccchips", player2.getAccchip());// 상대 누적칩
			
			request.setAttribute("gameEndFlag", gameEndFlag);			// 게임 종료 flag 
			
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
			request.setAttribute("mycard", Dealer.card2);				// 각종 정보들을 가지고
			request.setAttribute("enemycard", Dealer.card1);
			
			request.setAttribute("myleftchip", player2.getChips());		// 나의 남은칩과
			request.setAttribute("enemyleftchip", player1.getChips());	// 상대방의 남은칩과
			request.setAttribute("enemybetchip", player1.getBetchip());	// 상대방이 베팅한 칩 정보를 들고
			request.setAttribute("myaccchips", player2.getAccchip());	// 나의 누적칩
			request.setAttribute("enemyaccchips", player1.getAccchip());// 상대 누적칩
			
			request.setAttribute("gameEndFlag", gameEndFlag);			// 게임 종료 flag
			
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
		// 현재 플레이어가 포기했을 경우  //
		if(player1.getLose()!=0 || player2.getLose()!=0)
		{
			if(nowP==1)			// 1 플레이어인 경우
			{
				dealer.checkRound(player1,player2,Dealer.card1);	// 딜러가 라운드 체크
				if(Dealer.card1 == 10)	// 포기한 플레이어의 카드가 10인 경우 
					penalty = nowP + " 플레이어가10카드이므로 패널티가 적용됩니다.";
			}
			
			else			// 2 플레이어인 경우
			{
				dealer.checkRound(player2,player1,Dealer.card2);	// 딜러가 라운드 체크
				if(Dealer.card2 == 10)	// 포기한 플레이어의 카드가 10인 경우 
					penalty = nowP + " 플레이어가10카드이므로 패널티가 적용됩니다.";
			}
			open="open";	// 카드 오픈 메세지
		}
		// 같은 칩 배팅한 경우  //
		else
		{
			dealer.checkSamechipRound(player1, player2);	// 딜러가 라운드 체크 (같은 칩 배팅인 경우)
			if(Dealer.card1 > Dealer.card2)		// 플레이어 1의 카드가 큰 경우
			{
				losemsg = saveid[1]+"님이 패하였습니다.";
				winmsg = saveid[0]+"님이 승리하였습니다.";
			}
			else if(Dealer.card1 < Dealer.card2)// 플레이어 2의 카드가 큰 경우
			{
				losemsg = saveid[0]+"님이 패하였습니다.";
				winmsg = saveid[1]+"님이 승리하였습니다.";	
			}
			else {
				winmsg= "무승부입니다.";			// 카드가 같은 경우
			}
			open="open";	// 카드 오픈 메세지
		}
		
		/* 사용자의 칩수를 확인해여 0보다 작거나 같으면 게임 종료  */
		if(player1.getChips() <=0)
		{
			losemsg = saveid[0]+" 님의 칩이 0개가 되었습니다.";
			winmsg = saveid[1]+" 님이 최종승리하셨습니다.";
			gameEndFlag = "true";
		}
		else if(player2.getChips() <=0)
		{
			losemsg = saveid[1]+" 님의 칩이 0개가 되었습니다.";
			winmsg = saveid[0]+" 님이 최종승리하셨습니다.";
			gameEndFlag = "true";
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		doGet(request, response);
		
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
			if(saveid[1].equals(saveid[0])) {						// 중복된 아이디로 로그인할시
				saveid[1] = "";									
				index=(index+1) % 2;					
				out.println("<script type='text/javascript'>"	// 돌려보냄		
				+ "alert('사용중인 닉네임입니다.');history.back();</script>");	
			}
		}	
		
		if(saveid[1]!="") 			// 2명이 로그인했다면 게임 시작
		{				
			if(roundFlag==0)
			{						// 새 라운드가 시작될때 딱 한번만 실행되는 조건문
				stage++;			// 라운드 증가
				System.out.printf("<%d라운드를 시작합니다>\n",stage);
				if(stage%10==1)	dealer.initCardDeck();			// 카드덱 생성(1~10까지 각각2장씩 존재)
				dealer.distributeCard(player1, player2);		// 플레이어별로 카드를 뽑아주고
				dealer.initRound(player1, player2);				// 기본베팅을 자동으로 해준다
				
				loop=0;				// 반복제어 변수 초기화
				gameclosed=0;		// 게임종료 상태를 게임 진행중상태로 변경
				rejoin=0;			// 게임을 마친 인원수를 저장할 변수 초기화
				refreshcount=0;		// 상대베팅확인 횟수 초기화
				
				messageInit();
				
				roundFlag=1;				// 다음 라운드까지 해당 조건문은 실행되지 않음
			}
			
			if(loop<2) 			// 유저들을 게임상태창으로 보내는 조건문
			{					
				if(userOrder==1) 		// 현재 순서가 플레이어 1일 경우
				{													
					if(playTurn<1) {			// 첫 라운드때 딱 한번만
						userOrder=0;	// 플레이어 순서 조작 (2번째 로그인한 사람이 후플레이어가 되게 하기 위함)
						playTurn++;			// 해당 조건문은 다시는 실행되지 않음
					}
					request.setAttribute("mycard", Dealer.card1);				// 각종 정보들을 가지고
					request.setAttribute("enemycard", Dealer.card2);
					request.setAttribute("enemyid", saveid[1]);
					request.setAttribute("myleftchip", player1.getChips());
					request.setAttribute("enemyleftchip", player2.getChips());	// jsp로 이동
					request.getRequestDispatcher("playeroutput.jsp").forward(request, response);
					loop++;			// 반복제어 변수 증가
					userOrder++;	// 플레이어 순서 변경
				}
				else if(userOrder==2) 	// 현재 순서가 플레이어 2일 경우	 	
				{						// 초기에는 2번째 로그인한 사람이 해당 조건문에 먼저 접근하므로 player2로 세팅됨 
					request.setAttribute("mycard", Dealer.card2);				// 각종 정보들을 가지고
					request.setAttribute("enemycard", Dealer.card1);			
					request.setAttribute("enemyid", saveid[0]);					
					request.setAttribute("myleftchip", player2.getChips());		
					request.setAttribute("enemyleftchip", player1.getChips());	//jsp로 이동
					request.getRequestDispatcher("playeroutput.jsp").forward(request, response);
					loop++;			// 반복제어 변수 증가
					userOrder--;	// 플레이어 순서 변경
				}
			}
		}
		
		String betchip = request.getParameter("betchip");			// 베팅창에 입력한 값을 저장할 변수
		String checkid = request.getParameter("checkid");			// 베팅을 한 사용자id를 저장할 변수
		String betrefresh = request.getParameter("checkenemybet");	// 베팅현황을 새로고침할때 사용할 변수
		String nextround = request.getParameter("nextround");		// 라운드 종료 여부를 저장할 변수
	
		// '다음라운드'를 누르고 온 경우 //
		if(nextround!=null) {					
			if(userOrder==1 && checkid.equals(saveid[0])) {		// 현재순서는 1번 플레이어이고 id까지 맞다면 
				rejoin++;										// 게임을 마친 인원수 증가
				userOrder++;									// 플레이어 순서 변경
			}
			else if(userOrder==2 && checkid.equals(saveid[1])) {	// 현재순서는 2번 플레이어이고 id까지 맞다면 
				rejoin++;										// 게임을 마친 인원수 증가
				userOrder--;									// 플레이어 순서 변경		
			}
			else out.println("<script type='text/javascript'>"		// 해당 차례가 아닐때 라운드 종료하려 할 시
					+ "alert('잠시후에 다시 시도하세요');history.back();</script>");	// 돌려보냄
			if(rejoin==2) roundFlag=0;						// 게임을 마친 인원수가 2명이면 새 라운드 시작
		}
		
		// '상대베팅확인'을 누르고 온 경우 //
		if(betrefresh!=null) {		
						
			if(userOrder==1 && checkid.equals(saveid[0])) 
				showThings(request, response, 0);				// 1번유저가 현재 순서가 1번인데 '상대베팅확인' 눌렀을시
																// 출력창 새로고침
			
			else if(userOrder==2 && checkid.equals(saveid[0])) {// 1번유저가 현재 순서가 2번인데 '상대베팅확인' 눌렀을시
				refreshcount++;									// 상대베팅확인을 누른 횟수 누적
				if(refreshcount>=30) {							// 30번확인시 상대가 나간것으로 판단
					gameEndFlag = "true";						// 게임종료 플래그 설정
					losemsg = saveid[1]+" 님이 게임에서 나가셨습니다.";	
					winmsg = saveid[0]+" 님이 최종승리하셨습니다.";
				}
				showThings(request, response, 0);				// 출력창 새로고침
			}
						
			else if(userOrder==1 && checkid.equals(saveid[1])) {// 2번유저가 현재 순서가 1번인데 '상대베팅확인' 눌렀을시
				refreshcount++;									// 상대베팅확인을 누른 횟수 누적
				if(refreshcount>=30) {							// 30번확인시 상대가 나간것으로 판단
					gameEndFlag = "true";						// 게임종료 플래그 설정
					losemsg = saveid[0]+" 님이 게임에서 나가셨습니다.";	
					winmsg = saveid[1]+" 님이 최종승리하셨습니다.";
				}
				showThings(request, response,1);				// 출력창 새로고침
			}
			
			else if(userOrder==2 && checkid.equals(saveid[1]))	// 2번유저가 현재 순서가 2번인데 '상대베팅확인' 눌렀을시
				showThings(request, response,1);				// 출력창 새로고침
			
			System.out.printf("상대베팅확인 횟수 : %d\n", refreshcount);
			
			if(gameEndFlag.equals("true"))
			{	
				init();	// 게임의 최종승자가 결정된경우 모든 변수를 초기화시켜 재시작을 할수있도록함
			}
		}

		// '배팅'을 누르고 온 경우 //
		if(checkid!=null && betchip!=null)		
		{	
			if(betchip.isEmpty()==true) {				// 입력값이 없이 왔다면 다시 돌려보냄
				out.println("<script type='text/javascript'>"
				+ "alert('입력값이 없습니다!');history.back();</script>");
			}
			else if(gameclosed==1) {				// 게임이 이미 종료되었을 경우에도 다시 돌려보냄
				out.println("<script type='text/javascript'>"
					+ "alert('게임이 종료되었습니다. 다음라운드를 시작하세요.');history.back();</script>");
			}
			else {								// 아직까지 게임이 진행중일때 
				if(userOrder==1 && checkid.equals(saveid[0]))		// 현재순서는 1번 플레이어이고 id까지 맞다면 
				{	
					player1.setBetchip(betchip);					// 베팅한 값을 int로 변환
					int checkbetting = player1.betChips();	// 베팅 가능 여부를 확인 (betChips함수 참고)
					
					// 잘못배팅한 경우 - 딜러가 체크해주는 경우  //
					if(player1.getAccchip()<player2.getAccchip() && checkbetting!=1 && checkbetting!=2 && checkbetting!=-1)	
					{	
						out.println("<script type='text/javascript'>"
						+ "alert('상대방의 누적된 칩수와 같거나 크게 배팅해야합니다.');history.back();</script>");
						player1.wrongBetchips(); 				// 잘못 배팅했으므로 칩 원상복귀
					}
					
					else if(player1.getBetchip()>=player2.getChips()+player2.getAccchip() && checkbetting!=1 && checkbetting!=2 && checkbetting!=-1) 
					{	
						out.println("<script type='text/javascript'>"
						+ "alert('상대의 남은칩보다 더 베팅할 수 없습니다.');history.back();</script>");
						player1.wrongBetchips(); 
					}
									
					else {					// -스스로 바보같이 배팅했는지 체크
																			// 남은 칩 갯수가 총1개일때 0이 베팅가능하도록...
						if(player1.getChips()+player1.getAccchip()==1 && player1.getBetchip()==0) checkbetting = 3;
						else if(player2.getChips()+player2.getAccchip()==1 && player1.getBetchip()==0) checkbetting = 3;
						
						if(checkbetting==-1) {				// 숫자입력이 아닐 경우 다시 돌려보냄 
							out.println("<script type='text/javascript'>"
							+ "alert('숫자만 입력가능합니다.');history.back();</script>");
						}
						else if(checkbetting==1) {			// 보유칩보다 많이 배팅한 경우 다시 돌려보냄
							out.println("<script type='text/javascript'>"
							+ "alert('내가 가진 칩 수보다 더 베팅할 수 없습니다.');history.back();</script>");
						}
						else if(checkbetting==2) {			// 배팅 포기한 경우 
							player1.changeLose(0);						// 라운드 포기 표시하기
							RoundResult(userOrder);						// 라운드 결과 확인 함수	
							userOrder++;								// 플레이어 순서 변경	
							refreshcount=0;								// 상대베팅확인 횟수 초기화
							gameclosed=1;								// '게임 종료'상태로 변경
							losemsg = saveid[0]+"님이 포기하셨습니다.";
							winmsg = saveid[1] +"님이 승리하였습니다.";
							showThings(request,response,0);				// 상태 메세지들 출력
						}
						else if(checkbetting==3) {		// 정상적으로 배팅한 경우 
							userOrder++;				// 플레이어 순서 변경
							refreshcount=0;				// 상대베팅확인 횟수 초기화
							
							if(dealer.checkSame(player1, player2)!=0) {	// 배팅된 칩이 같은 개수인 경우
								RoundResult(userOrder);					// 라운드 결과 확인 함수
								gameclosed=1;							// '게임 종료'상태로 변경
								msg = "같은수를 배팅하셨습니다.";
							}
							showThings(request,response,0);				// 상태 메세지들 출력
						}
					}
				}
			
				else if(userOrder==2 && checkid.equals(saveid[1]))	// 현재순서는 2번 플레이어이고 id까지 맞다면
				{	
					player2.setBetchip(betchip);						// 베팅한 값을 int로 변환
					int checkbetting = player2.betChips();	// 베팅 가능 여부를 확인 (betChips함수 참고)
					
					// 잘못배팅한 경우 - 딜러가 체크해주는 경우  //
					if(player2.getAccchip()<player1.getAccchip() && checkbetting!=1 && checkbetting!=2 && checkbetting!=-1)
					{	
						out.println("<script type='text/javascript'>"
						+ "alert('상대방의 누적된 칩수와 같거나 크게 배팅해야합니다.');history.back();</script>");
						player2.wrongBetchips(); 				// 잘못 배팅했으므로 칩 원상복귀
					}
					
					else if(player2.getBetchip()>=player1.getChips()+player1.getAccchip() && checkbetting!=1 && checkbetting!=2 && checkbetting!=-1) 
					{	
						out.println("<script type='text/javascript'>"
						+ "alert('상대의 남은칩보다 더 베팅할 수 없습니다.');history.back();</script>");
						player2.wrongBetchips(); 
					}
					
					else { 			// -스스로 바보같이 배팅안했는지 체크
																			// 남은 칩 갯수가 총1개일때 0이 베팅가능하도록...
						if(player1.getChips()+player1.getAccchip()==1 && player2.getBetchip()==0) checkbetting = 3;
						else if(player2.getChips()+player2.getAccchip()==1 && player2.getBetchip()==0) checkbetting = 3;
						
						if(checkbetting==-1) {
							out.println("<script type='text/javascript'>"
							+ "alert('숫자만 입력가능합니다.');history.back();</script>");
						}
						else if(checkbetting==1) {			// 보유칩보다 많이 배팅한 경우 다시 돌려보냄
							out.println("<script type='text/javascript'>"
							+ "alert('내가 가진 칩 수보다 더 베팅할 수 없습니다.');history.back();</script>");
						}
						else if(checkbetting==2) { 		// 배팅 포기한 경우 
							player2.changeLose(0);						// 라운드 포기 표시하기
							RoundResult(userOrder);						// 라운드 결과 확인 함수
							userOrder--;								// 플레이어 순서 변경
							refreshcount=0;								// 상대베팅확인 횟수 초기화
							gameclosed=1;								// '게임 종료'상태로 변경
							losemsg = saveid[1]+"님이 포기하였습니다.";
							winmsg = saveid[0] +"님이 승리하였습니다.";
							showThings(request,response,1);				// 상태 메세지들 출력
						}
						else if(checkbetting==3) { 		// 정상적으로 배팅한 경우
							userOrder--;				// 플레이어 순서 변경
							refreshcount=0;				// 상대베팅확인 횟수 초기화
							
							if(dealer.checkSame(player1, player2)!=0) {	// 배팅된 칩이 같은 개수인 경우
								RoundResult(userOrder);					// 라운드 결과 확인 함수
								gameclosed=1;							// '게임 종료'상태로 변경
								msg = "같은수를 배팅하셨습니다.";
							}
							showThings(request,response,1);				// 상태 메세지들 출력
						}
					}
				}
				else out.println("<script type='text/javascript'>"		// 본인 차례가 아닐떄	베팅한 경우
					+ "alert('당신차례가아냐');history.back();</script>");
			}
		}
		
	out.println("<html><head>");							// 3초마다 GameServer 새로고침
	out.println("<meta http-equiv='refresh'content = '2; url=GameServer'>");
	/*** 배경화면을 위한 css설정  ***/
	out.println("<style type='text/css'>");
	out.println("html{height: 100%;}");
	out.println("body{position:relative; margin:0; padding:0; height: 100%}"
			+ "#bg{position:absolute; top:0; left:0; width:100%; height:100%;"
			+ "z-index:-1; overflow:hidden}</style>");
	out.println("</head><body text=white>"); // 출력 텍스트의 색을 흰색으로 한다.						
	out.println("<div id='bg'><img src='./img/waitroom.png' alt='' width='100%' height='100%'></div>");
	out.println("<br><br><br><br><br><br><br><br><br>");
	/* 게임이 진행중이지 않을때에는 대기화면을 출력해주고 게임 진행중에는 다음 라운드 시작전에 출력  */
	if(rejoin==0) out.println("<center><h3>상대를 기다리는 중입니다....</h3></center>");
	else out.println("<center><h3>다음 라운드 로딩중.....</h3></center>");
	out.println("<center>--현재 접속중인 플레이어--<br></center>");
	for(int i=0; i<2; i++) out.println("<center>"+saveid[i]+"</center><br>");	// 모든 로그인 확인 메시지 출력
	out.println("</body></html>");
	out.close();	

	}
}
