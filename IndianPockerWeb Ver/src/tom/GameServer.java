package tom;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**Servlet implementation class GameServer**/

public class GameServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static int userOrder;					// player ���� ���� ����	
	String saveid[];						// player id�� ������ �迭
	int index;
		
	// ���� �޼��� ����ִ� ������  //
	String msg;
	String winmsg;	
	String losemsg;
	String penalty;
	String open;
	
	String gameEndFlag;	// ���� ���� flag
	
	/* ���� ������ ���� */
	int roundFlag;				
	int playTurn;
	int loop;
	int gameclosed;
	int stage;
	int die;
	int rejoin;
	int refreshcount;			// ��뺣��Ȯ�� Ƚ��
		
	// Dealer ��ü�� Player ��ü ����  // 
	Dealer dealer = new Dealer();
	Players player1 = new Players();			
	Players player2 = new Players();
	
	//***************************************�ʱ�ȭ �Լ�***************************************//
	public void init() 
	{
		player1.Init(1);	//Player �ʱ�ȭ			
		player2.Init(2);
		
		stage=0;
		die=0;
		index = 0;
		rejoin=0;
		refreshcount=0;
		
		roundFlag=0;		// ���� ������ �����ϴ� flag
		playTurn=0;			// player�� ������ �����ϴ� flag
		loop=0;				// ������ �ݺ��ϴ� ����
		gameEndFlag="false"; // false �� ���������� true�� ���� ����
		messageInit();
		
		userOrder = 2;						// 2��°�α����ϴ� ���� ���� �ڵ忡 �����ϹǷ�... 
		saveid = new String[2];
		for(int i=0; i<2; i++) saveid[i] = "";
	}
	
	public void messageInit()
	{
		// �޽��� �ʱ�ȭ
		msg="";
		losemsg="";
		winmsg="";
		penalty="";
		open="";
	}
	//***************************************���� ������� �Լ�***************************************//
	public void showThings(HttpServletRequest request, HttpServletResponse response,int flag) 
			throws ServletException, IOException
	{
		if(flag==0)			// player1���� ����ϴ� ���
		{
			request.setAttribute("mycard", Dealer.card1);				// ���� �������� ������
			request.setAttribute("enemycard", Dealer.card2);
			
			request.setAttribute("myleftchip", player1.getChips());		// ���� ����Ĩ��
			request.setAttribute("enemyleftchip", player2.getChips());	// ������ ����Ĩ��
			request.setAttribute("enemybetchip", player2.getBetchip());	// ������ ������ Ĩ ������ ���
			request.setAttribute("myaccchips", player1.getAccchip());	// ���� ����Ĩ
			request.setAttribute("enemyaccchips", player2.getAccchip());// ��� ����Ĩ
			
			request.setAttribute("gameEndFlag", gameEndFlag);			// ���� ���� flag 
			
			request.setAttribute("nowplayer", userOrder);				// ���� ���� ����
			request.setAttribute("msg", msg);							// ���� �޼���
			request.setAttribute("losemsg", losemsg);					// �� �޽���
			request.setAttribute("winmsg", winmsg);						// �̱� �޽���
			request.setAttribute("penalty", penalty);					// ���Ƽ �޽���
			request.setAttribute("open", open);							// ī�� ���� 
			
			request.getRequestDispatcher("playeroutput.jsp").forward(request, response);	// �ٽ� jsp�� �̵�
		}
		else				// player2���� ����ϴ� ���
		{
			request.setAttribute("mycard", Dealer.card2);				// ���� �������� ������
			request.setAttribute("enemycard", Dealer.card1);
			
			request.setAttribute("myleftchip", player2.getChips());		// ���� ����Ĩ��
			request.setAttribute("enemyleftchip", player1.getChips());	// ������ ����Ĩ��
			request.setAttribute("enemybetchip", player1.getBetchip());	// ������ ������ Ĩ ������ ���
			request.setAttribute("myaccchips", player2.getAccchip());	// ���� ����Ĩ
			request.setAttribute("enemyaccchips", player1.getAccchip());// ��� ����Ĩ
			
			request.setAttribute("gameEndFlag", gameEndFlag);			// ���� ���� flag
			
			request.setAttribute("nowplayer", userOrder);				// ���� ���� ����
			request.setAttribute("msg", msg);							// ���� �޼���
			request.setAttribute("losemsg", losemsg);					// �� �޽���
			request.setAttribute("winmsg", winmsg);						// �̱� �޽���
			request.setAttribute("penalty", penalty);					// ���Ƽ �޽���
			request.setAttribute("open", open);							// ī�� ���� 
			
			request.getRequestDispatcher("playeroutput.jsp").forward(request, response);	// �ٽ� jsp�� �̵�
		}
	}

	//***************************************�� ���� ��� �Լ�***************************************//
	public void RoundResult(int nowP)
	{
		// ���� �÷��̾ �������� ���  //
		if(player1.getLose()!=0 || player2.getLose()!=0)
		{
			if(nowP==1)			// 1 �÷��̾��� ���
			{
				dealer.checkRound(player1,player2,Dealer.card1);	// ������ ���� üũ
				if(Dealer.card1 == 10)	// ������ �÷��̾��� ī�尡 10�� ��� 
					penalty = nowP + " �÷��̾10ī���̹Ƿ� �г�Ƽ�� ����˴ϴ�.";
			}
			
			else			// 2 �÷��̾��� ���
			{
				dealer.checkRound(player2,player1,Dealer.card2);	// ������ ���� üũ
				if(Dealer.card2 == 10)	// ������ �÷��̾��� ī�尡 10�� ��� 
					penalty = nowP + " �÷��̾10ī���̹Ƿ� �г�Ƽ�� ����˴ϴ�.";
			}
			open="open";	// ī�� ���� �޼���
		}
		// ���� Ĩ ������ ���  //
		else
		{
			dealer.checkSamechipRound(player1, player2);	// ������ ���� üũ (���� Ĩ ������ ���)
			if(Dealer.card1 > Dealer.card2)		// �÷��̾� 1�� ī�尡 ū ���
			{
				losemsg = saveid[1]+"���� ���Ͽ����ϴ�.";
				winmsg = saveid[0]+"���� �¸��Ͽ����ϴ�.";
			}
			else if(Dealer.card1 < Dealer.card2)// �÷��̾� 2�� ī�尡 ū ���
			{
				losemsg = saveid[0]+"���� ���Ͽ����ϴ�.";
				winmsg = saveid[1]+"���� �¸��Ͽ����ϴ�.";	
			}
			else {
				winmsg= "���º��Դϴ�.";			// ī�尡 ���� ���
			}
			open="open";	// ī�� ���� �޼���
		}
		
		/* ������� Ĩ���� Ȯ���ؿ� 0���� �۰ų� ������ ���� ����  */
		if(player1.getChips() <=0)
		{
			losemsg = saveid[0]+" ���� Ĩ�� 0���� �Ǿ����ϴ�.";
			winmsg = saveid[1]+" ���� �����¸��ϼ̽��ϴ�.";
			gameEndFlag = "true";
		}
		else if(player2.getChips() <=0)
		{
			losemsg = saveid[1]+" ���� Ĩ�� 0���� �Ǿ����ϴ�.";
			winmsg = saveid[0]+" ���� �����¸��ϼ̽��ϴ�.";
			gameEndFlag = "true";
		}
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		doGet(request, response);
		
	}
	//***************************************���� ���� �Լ�***************************************//
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		request.setCharacterEncoding("KSC5601");
		response.setContentType("text/html;charset=KSC5601");			// ���ڵ� ����.....
		PrintWriter out = response.getWriter();
		
		String userid = request.getParameter("userid");			// user�� id�� �޾ƿͼ�
		if(userid!=null) {										// id�� null�̾ƴ϶�� (�翬�� �ƴϰ�����..)
			synchronized (saveid) {							// �ѹ��� �Ѹ�
				saveid[index] = userid;		
				index=(index+1) % 2;
			}
			if(saveid[1].equals(saveid[0])) {						// �ߺ��� ���̵�� �α����ҽ�
				saveid[1] = "";									
				index=(index+1) % 2;					
				out.println("<script type='text/javascript'>"	// ��������		
				+ "alert('������� �г����Դϴ�.');history.back();</script>");	
			}
		}	
		
		if(saveid[1]!="") 			// 2���� �α����ߴٸ� ���� ����
		{				
			if(roundFlag==0)
			{						// �� ���尡 ���۵ɶ� �� �ѹ��� ����Ǵ� ���ǹ�
				stage++;			// ���� ����
				System.out.printf("<%d���带 �����մϴ�>\n",stage);
				if(stage%10==1)	dealer.initCardDeck();			// ī�嵦 ����(1~10���� ����2�徿 ����)
				dealer.distributeCard(player1, player2);		// �÷��̾�� ī�带 �̾��ְ�
				dealer.initRound(player1, player2);				// �⺻������ �ڵ����� ���ش�
				
				loop=0;				// �ݺ����� ���� �ʱ�ȭ
				gameclosed=0;		// �������� ���¸� ���� �����߻��·� ����
				rejoin=0;			// ������ ��ģ �ο����� ������ ���� �ʱ�ȭ
				refreshcount=0;		// ��뺣��Ȯ�� Ƚ�� �ʱ�ȭ
				
				messageInit();
				
				roundFlag=1;				// ���� ������� �ش� ���ǹ��� ������� ����
			}
			
			if(loop<2) 			// �������� ���ӻ���â���� ������ ���ǹ�
			{					
				if(userOrder==1) 		// ���� ������ �÷��̾� 1�� ���
				{													
					if(playTurn<1) {			// ù ���嶧 �� �ѹ���
						userOrder=0;	// �÷��̾� ���� ���� (2��° �α����� ����� ���÷��̾ �ǰ� �ϱ� ����)
						playTurn++;			// �ش� ���ǹ��� �ٽô� ������� ����
					}
					request.setAttribute("mycard", Dealer.card1);				// ���� �������� ������
					request.setAttribute("enemycard", Dealer.card2);
					request.setAttribute("enemyid", saveid[1]);
					request.setAttribute("myleftchip", player1.getChips());
					request.setAttribute("enemyleftchip", player2.getChips());	// jsp�� �̵�
					request.getRequestDispatcher("playeroutput.jsp").forward(request, response);
					loop++;			// �ݺ����� ���� ����
					userOrder++;	// �÷��̾� ���� ����
				}
				else if(userOrder==2) 	// ���� ������ �÷��̾� 2�� ���	 	
				{						// �ʱ⿡�� 2��° �α����� ����� �ش� ���ǹ��� ���� �����ϹǷ� player2�� ���õ� 
					request.setAttribute("mycard", Dealer.card2);				// ���� �������� ������
					request.setAttribute("enemycard", Dealer.card1);			
					request.setAttribute("enemyid", saveid[0]);					
					request.setAttribute("myleftchip", player2.getChips());		
					request.setAttribute("enemyleftchip", player1.getChips());	//jsp�� �̵�
					request.getRequestDispatcher("playeroutput.jsp").forward(request, response);
					loop++;			// �ݺ����� ���� ����
					userOrder--;	// �÷��̾� ���� ����
				}
			}
		}
		
		String betchip = request.getParameter("betchip");			// ����â�� �Է��� ���� ������ ����
		String checkid = request.getParameter("checkid");			// ������ �� �����id�� ������ ����
		String betrefresh = request.getParameter("checkenemybet");	// ������Ȳ�� ���ΰ�ħ�Ҷ� ����� ����
		String nextround = request.getParameter("nextround");		// ���� ���� ���θ� ������ ����
	
		// '��������'�� ������ �� ��� //
		if(nextround!=null) {					
			if(userOrder==1 && checkid.equals(saveid[0])) {		// ��������� 1�� �÷��̾��̰� id���� �´ٸ� 
				rejoin++;										// ������ ��ģ �ο��� ����
				userOrder++;									// �÷��̾� ���� ����
			}
			else if(userOrder==2 && checkid.equals(saveid[1])) {	// ��������� 2�� �÷��̾��̰� id���� �´ٸ� 
				rejoin++;										// ������ ��ģ �ο��� ����
				userOrder--;									// �÷��̾� ���� ����		
			}
			else out.println("<script type='text/javascript'>"		// �ش� ���ʰ� �ƴҶ� ���� �����Ϸ� �� ��
					+ "alert('����Ŀ� �ٽ� �õ��ϼ���');history.back();</script>");	// ��������
			if(rejoin==2) roundFlag=0;						// ������ ��ģ �ο����� 2���̸� �� ���� ����
		}
		
		// '��뺣��Ȯ��'�� ������ �� ��� //
		if(betrefresh!=null) {		
						
			if(userOrder==1 && checkid.equals(saveid[0])) 
				showThings(request, response, 0);				// 1�������� ���� ������ 1���ε� '��뺣��Ȯ��' ��������
																// ���â ���ΰ�ħ
			
			else if(userOrder==2 && checkid.equals(saveid[0])) {// 1�������� ���� ������ 2���ε� '��뺣��Ȯ��' ��������
				refreshcount++;									// ��뺣��Ȯ���� ���� Ƚ�� ����
				if(refreshcount>=30) {							// 30��Ȯ�ν� ��밡 ���������� �Ǵ�
					gameEndFlag = "true";						// �������� �÷��� ����
					losemsg = saveid[1]+" ���� ���ӿ��� �����̽��ϴ�.";	
					winmsg = saveid[0]+" ���� �����¸��ϼ̽��ϴ�.";
				}
				showThings(request, response, 0);				// ���â ���ΰ�ħ
			}
						
			else if(userOrder==1 && checkid.equals(saveid[1])) {// 2�������� ���� ������ 1���ε� '��뺣��Ȯ��' ��������
				refreshcount++;									// ��뺣��Ȯ���� ���� Ƚ�� ����
				if(refreshcount>=30) {							// 30��Ȯ�ν� ��밡 ���������� �Ǵ�
					gameEndFlag = "true";						// �������� �÷��� ����
					losemsg = saveid[0]+" ���� ���ӿ��� �����̽��ϴ�.";	
					winmsg = saveid[1]+" ���� �����¸��ϼ̽��ϴ�.";
				}
				showThings(request, response,1);				// ���â ���ΰ�ħ
			}
			
			else if(userOrder==2 && checkid.equals(saveid[1]))	// 2�������� ���� ������ 2���ε� '��뺣��Ȯ��' ��������
				showThings(request, response,1);				// ���â ���ΰ�ħ
			
			System.out.printf("��뺣��Ȯ�� Ƚ�� : %d\n", refreshcount);
			
			if(gameEndFlag.equals("true"))
			{	
				init();	// ������ �������ڰ� �����Ȱ�� ��� ������ �ʱ�ȭ���� ������� �Ҽ��ֵ�����
			}
		}

		// '����'�� ������ �� ��� //
		if(checkid!=null && betchip!=null)		
		{	
			if(betchip.isEmpty()==true) {				// �Է°��� ���� �Դٸ� �ٽ� ��������
				out.println("<script type='text/javascript'>"
				+ "alert('�Է°��� �����ϴ�!');history.back();</script>");
			}
			else if(gameclosed==1) {				// ������ �̹� ����Ǿ��� ��쿡�� �ٽ� ��������
				out.println("<script type='text/javascript'>"
					+ "alert('������ ����Ǿ����ϴ�. �������带 �����ϼ���.');history.back();</script>");
			}
			else {								// �������� ������ �������϶� 
				if(userOrder==1 && checkid.equals(saveid[0]))		// ��������� 1�� �÷��̾��̰� id���� �´ٸ� 
				{	
					player1.setBetchip(betchip);					// ������ ���� int�� ��ȯ
					int checkbetting = player1.betChips();	// ���� ���� ���θ� Ȯ�� (betChips�Լ� ����)
					
					// �߸������� ��� - ������ üũ���ִ� ���  //
					if(player1.getAccchip()<player2.getAccchip() && checkbetting!=1 && checkbetting!=2 && checkbetting!=-1)	
					{	
						out.println("<script type='text/javascript'>"
						+ "alert('������ ������ Ĩ���� ���ų� ũ�� �����ؾ��մϴ�.');history.back();</script>");
						player1.wrongBetchips(); 				// �߸� ���������Ƿ� Ĩ ���󺹱�
					}
					
					else if(player1.getBetchip()>=player2.getChips()+player2.getAccchip() && checkbetting!=1 && checkbetting!=2 && checkbetting!=-1) 
					{	
						out.println("<script type='text/javascript'>"
						+ "alert('����� ����Ĩ���� �� ������ �� �����ϴ�.');history.back();</script>");
						player1.wrongBetchips(); 
					}
									
					else {					// -������ �ٺ����� �����ߴ��� üũ
																			// ���� Ĩ ������ ��1���϶� 0�� ���ð����ϵ���...
						if(player1.getChips()+player1.getAccchip()==1 && player1.getBetchip()==0) checkbetting = 3;
						else if(player2.getChips()+player2.getAccchip()==1 && player1.getBetchip()==0) checkbetting = 3;
						
						if(checkbetting==-1) {				// �����Է��� �ƴ� ��� �ٽ� �������� 
							out.println("<script type='text/javascript'>"
							+ "alert('���ڸ� �Է°����մϴ�.');history.back();</script>");
						}
						else if(checkbetting==1) {			// ����Ĩ���� ���� ������ ��� �ٽ� ��������
							out.println("<script type='text/javascript'>"
							+ "alert('���� ���� Ĩ ������ �� ������ �� �����ϴ�.');history.back();</script>");
						}
						else if(checkbetting==2) {			// ���� ������ ��� 
							player1.changeLose(0);						// ���� ���� ǥ���ϱ�
							RoundResult(userOrder);						// ���� ��� Ȯ�� �Լ�	
							userOrder++;								// �÷��̾� ���� ����	
							refreshcount=0;								// ��뺣��Ȯ�� Ƚ�� �ʱ�ȭ
							gameclosed=1;								// '���� ����'���·� ����
							losemsg = saveid[0]+"���� �����ϼ̽��ϴ�.";
							winmsg = saveid[1] +"���� �¸��Ͽ����ϴ�.";
							showThings(request,response,0);				// ���� �޼����� ���
						}
						else if(checkbetting==3) {		// ���������� ������ ��� 
							userOrder++;				// �÷��̾� ���� ����
							refreshcount=0;				// ��뺣��Ȯ�� Ƚ�� �ʱ�ȭ
							
							if(dealer.checkSame(player1, player2)!=0) {	// ���õ� Ĩ�� ���� ������ ���
								RoundResult(userOrder);					// ���� ��� Ȯ�� �Լ�
								gameclosed=1;							// '���� ����'���·� ����
								msg = "�������� �����ϼ̽��ϴ�.";
							}
							showThings(request,response,0);				// ���� �޼����� ���
						}
					}
				}
			
				else if(userOrder==2 && checkid.equals(saveid[1]))	// ��������� 2�� �÷��̾��̰� id���� �´ٸ�
				{	
					player2.setBetchip(betchip);						// ������ ���� int�� ��ȯ
					int checkbetting = player2.betChips();	// ���� ���� ���θ� Ȯ�� (betChips�Լ� ����)
					
					// �߸������� ��� - ������ üũ���ִ� ���  //
					if(player2.getAccchip()<player1.getAccchip() && checkbetting!=1 && checkbetting!=2 && checkbetting!=-1)
					{	
						out.println("<script type='text/javascript'>"
						+ "alert('������ ������ Ĩ���� ���ų� ũ�� �����ؾ��մϴ�.');history.back();</script>");
						player2.wrongBetchips(); 				// �߸� ���������Ƿ� Ĩ ���󺹱�
					}
					
					else if(player2.getBetchip()>=player1.getChips()+player1.getAccchip() && checkbetting!=1 && checkbetting!=2 && checkbetting!=-1) 
					{	
						out.println("<script type='text/javascript'>"
						+ "alert('����� ����Ĩ���� �� ������ �� �����ϴ�.');history.back();</script>");
						player2.wrongBetchips(); 
					}
					
					else { 			// -������ �ٺ����� ���þ��ߴ��� üũ
																			// ���� Ĩ ������ ��1���϶� 0�� ���ð����ϵ���...
						if(player1.getChips()+player1.getAccchip()==1 && player2.getBetchip()==0) checkbetting = 3;
						else if(player2.getChips()+player2.getAccchip()==1 && player2.getBetchip()==0) checkbetting = 3;
						
						if(checkbetting==-1) {
							out.println("<script type='text/javascript'>"
							+ "alert('���ڸ� �Է°����մϴ�.');history.back();</script>");
						}
						else if(checkbetting==1) {			// ����Ĩ���� ���� ������ ��� �ٽ� ��������
							out.println("<script type='text/javascript'>"
							+ "alert('���� ���� Ĩ ������ �� ������ �� �����ϴ�.');history.back();</script>");
						}
						else if(checkbetting==2) { 		// ���� ������ ��� 
							player2.changeLose(0);						// ���� ���� ǥ���ϱ�
							RoundResult(userOrder);						// ���� ��� Ȯ�� �Լ�
							userOrder--;								// �÷��̾� ���� ����
							refreshcount=0;								// ��뺣��Ȯ�� Ƚ�� �ʱ�ȭ
							gameclosed=1;								// '���� ����'���·� ����
							losemsg = saveid[1]+"���� �����Ͽ����ϴ�.";
							winmsg = saveid[0] +"���� �¸��Ͽ����ϴ�.";
							showThings(request,response,1);				// ���� �޼����� ���
						}
						else if(checkbetting==3) { 		// ���������� ������ ���
							userOrder--;				// �÷��̾� ���� ����
							refreshcount=0;				// ��뺣��Ȯ�� Ƚ�� �ʱ�ȭ
							
							if(dealer.checkSame(player1, player2)!=0) {	// ���õ� Ĩ�� ���� ������ ���
								RoundResult(userOrder);					// ���� ��� Ȯ�� �Լ�
								gameclosed=1;							// '���� ����'���·� ����
								msg = "�������� �����ϼ̽��ϴ�.";
							}
							showThings(request,response,1);				// ���� �޼����� ���
						}
					}
				}
				else out.println("<script type='text/javascript'>"		// ���� ���ʰ� �ƴҋ�	������ ���
					+ "alert('������ʰ��Ƴ�');history.back();</script>");
			}
		}
		
	out.println("<html><head>");							// 3�ʸ��� GameServer ���ΰ�ħ
	out.println("<meta http-equiv='refresh'content = '2; url=GameServer'>");
	/*** ���ȭ���� ���� css����  ***/
	out.println("<style type='text/css'>");
	out.println("html{height: 100%;}");
	out.println("body{position:relative; margin:0; padding:0; height: 100%}"
			+ "#bg{position:absolute; top:0; left:0; width:100%; height:100%;"
			+ "z-index:-1; overflow:hidden}</style>");
	out.println("</head><body text=white>"); // ��� �ؽ�Ʈ�� ���� ������� �Ѵ�.						
	out.println("<div id='bg'><img src='./img/waitroom.png' alt='' width='100%' height='100%'></div>");
	out.println("<br><br><br><br><br><br><br><br><br>");
	/* ������ ���������� ���������� ���ȭ���� ������ְ� ���� �����߿��� ���� ���� �������� ���  */
	if(rejoin==0) out.println("<center><h3>��븦 ��ٸ��� ���Դϴ�....</h3></center>");
	else out.println("<center><h3>���� ���� �ε���.....</h3></center>");
	out.println("<center>--���� �������� �÷��̾�--<br></center>");
	for(int i=0; i<2; i++) out.println("<center>"+saveid[i]+"</center><br>");	// ��� �α��� Ȯ�� �޽��� ���
	out.println("</body></html>");
	out.close();	

	}
}
