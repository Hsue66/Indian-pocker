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
	
	int y1, y2, y3, y4, y5;					// �׳��ʿ��Ҷ� ������ ���� ����...
	
	int stage;
	int die;
	
	// Dealer ��ü�� Player ��ü ����  // 
	Dealer dealer = new Dealer();
	Players player1 = new Players();			
	Players player2 = new Players();
	
	
	//***************************************�ʱ�ȭ �Լ�***************************************//
	public void init() 
	{
		player1.Init(1);	//Player �ʱ�ȭ			
		player2.Init(2);
		
		y1=0;		// ������ ������ ���� ���� �ʱ�ȭ
		y2=0;		// ������ ������ ���� ���� �ʱ�ȭ
		
		stage=0;
		die=0;
		index = 0;
		
		// �޽��� �ʱ�ȭ
		msg="";
		losemsg="";
		winmsg="";
		penalty="";
		open="";
		
		userOrder = 2;						// 2��°�α����ϴ� ���� ���� �ڵ忡 �����ϹǷ�... 
		saveid = new String[2];
		for(int i=0; i<2; i++) saveid[i] = "";
	}
	
	
	//***************************************���� ������� �Լ�***************************************//
	public void showThings(HttpServletRequest request, HttpServletResponse response,int flag) 
			throws ServletException, IOException
	{
		if(flag==0)			// player1���� ����ϴ� ���
		{
			request.setAttribute("myleftchip", player1.getChips());		// ���� ����Ĩ��
			request.setAttribute("enemyleftchip", player2.getChips());	// ������ ����Ĩ��
			request.setAttribute("enemybetchip", player2.getBetchip());	// ������ ������ Ĩ ������ ���
			request.setAttribute("myaccchips", player1.getAccchip());	// ���� ����Ĩ
			request.setAttribute("enemyaccchips", player2.getAccchip());// ��� ����Ĩ
			
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
			request.setAttribute("myleftchip", player2.getChips());		// ���� ����Ĩ��
			request.setAttribute("enemyleftchip", player1.getChips());	// ������ ����Ĩ��
			request.setAttribute("enemybetchip", player1.getBetchip());	// ������ ������ Ĩ ������ ���
			request.setAttribute("myaccchips", player2.getAccchip());	// ���� ����Ĩ
			request.setAttribute("enemyaccchips", player1.getAccchip());// ��� ����Ĩ
			
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
		System.out.println(nowP);
		
		// ���� �÷��̾ �������� ���  //
		if(player1.getLose()!=0 || player2.getLose()!=0)
		{
			if(nowP==1)			// 1 �÷��̾��� ���
			{
				dealer.checkRound(player1,player2,Dealer.card1);	// ������ ���� üũ
				
				if(Dealer.card1 == 10)	// ������ �÷��̾��� ī�尡 10�� ��� 
					penalty = nowP + " �÷��̾10ī���̹Ƿ� �г�Ƽ�� ����˴ϴ�.";
				
				System.out.println(player1.getChips());
				System.out.println(player2.getChips());
			}
			else			// 2 �÷��̾��� ���
			{
				dealer.checkRound(player2,player1,Dealer.card2);	// ������ ���� üũ
				
				if(Dealer.card2 == 10)	// ������ �÷��̾��� ī�尡 10�� ��� 
					penalty = nowP + " �÷��̾10ī���̹Ƿ� �г�Ƽ�� ����˴ϴ�.";
				
				System.out.println(player1.getChips());
				System.out.println(player2.getChips());
			}
			open="open";	// ī�� ���� �޼���
		}
		
		// ���� Ĩ ������ ���  //
		else
		{
			dealer.checkSamechipRound(player1, player2);	// ������ ���� üũ (���� Ĩ ������ ���)

			if(Dealer.card1 > Dealer.card2)		// �÷��̾� 1�� ī�尡 ū ���
			{
				losemsg = "2 �÷��̾ ���Ͽ����ϴ�.";
				winmsg = "1 �÷��̾ �¸��Ͽ����ϴ�.";
			}
			else if(Dealer.card1 < Dealer.card2)// �÷��̾� 2�� ī�尡 ū ���
			{
				losemsg = "1 �÷��̾ ���Ͽ����ϴ�.";
				winmsg = "2 �÷��̾ �¸��Ͽ����ϴ�.";	
			}
			else								// ī�尡 ���� ���
				winmsg= "���º��Դϴ�.";
			
			open="open";	// ī�� ���� �޼���
		}
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
		}	
		
		if(saveid[1]!="") 
		{				// 2���� �α����ߴٸ�!!!!!!
			if(y1<1)
			{				// �̺κ��� �ѹ��� �ݺ��ǵ��� ����..
					stage++;
					//System.out.printf("<%d���带 �����մϴ�>\n",stage);
					if(stage%10==1)	dealer.initCardDeck();			// ī�嵦�����
					dealer.distributeCard(player1, player2);		// �÷��̾�� ī��̾��ְ�
					dealer.initRound(player1, player2);				// �⺻������ ���ش�
					y1++;
			}
			
			if(y2<2) 
			{				// �̺κ��� 2���� �ݺ��ǵ��� ����..
				if(userOrder==1) 
				{			
																	// 2������ ������ 1���� ����
					request.setAttribute("mycard", Dealer.card1);
					request.setAttribute("enemycard", Dealer.card2);
					request.setAttribute("enemyid", saveid[1]);
					request.setAttribute("myleftchip", player1.getChips());
					request.setAttribute("enemyleftchip", player2.getChips());		// ������� 1���÷��̾ jsp�� �̵�
					request.getRequestDispatcher("playeroutput.jsp").forward(request, response);
					y2++;			// ���̻� �� �ڵ忣 �ȵ��ð���.. �Ƹ� 2��° ���� �� �ٽ� �ü���...	
				}
				else if(userOrder==2) 
				{					// %%%���������2�̹Ƿ� ������� ����%%%%
					request.setAttribute("mycard", Dealer.card2);				// ��=player2 �̴ϱ� card2)
					request.setAttribute("enemycard", Dealer.card1);			// ���� ī��
					request.setAttribute("enemyid", saveid[0]);					// ���� id
					request.setAttribute("myleftchip", player2.getChips());		// �� ���� Ĩ��
					request.setAttribute("enemyleftchip", player1.getChips());	// ���� ���� Ĩ��
					request.getRequestDispatcher("playeroutput.jsp").forward(request, response);// �� ������ ���  �÷��̾�2�� jsp�� �̵�
					userOrder--;		// ���� ����	
				}
			}
		}
		
		String temp = request.getParameter("betchip");			// jsp���� ������ �� ����
		String temp2 = request.getParameter("checkid");			// ����� ������ ����....
		String temp3 = request.getParameter("checkenemybet");	// ���� �������� �ҷ��ö� ���...
	
		
		// '��뺣��Ȯ��'�� ������ �� ��� //
		if(temp3!=null) 
		{											
			if(userOrder==1 && temp2.equals(saveid[0])) 			// ���� 1���ε�  �������ΰ��
				showThings(request,response,0);
			
			else if(userOrder==2 && temp2.equals(saveid[0])) 		// ���� 1���ε�  �������ΰ��
				showThings(request,response,0);
			
			else if(userOrder==2 && temp2.equals(saveid[1]))		// ���� 2���ε�  �������ΰ��
				showThings(request,response,1);
			
			else if(userOrder==1 && temp2.equals(saveid[1]))		// ���� 2���ε�  �������ΰ��
				showThings(request,response,1);
		}

		// ������ �� ��� //
		if(temp2!=null)
		{	
			if(temp1!=null)
			{
				if(userOrder==1 && temp2.equals(saveid[0]))		// 1�� ������ id���� ������
				{	
					player1.setBetchip(temp);					// ������ ���� int�� ��ȯ
					int checkbetting = player1.betChips();		// ���� ���� ���θ� Ȯ�� (betChips�Լ� ����)
				
					// �߸������� ��� - ������ üũ���ִ� ���  //
					if(player1.getAccchip()<player2.getAccchip() && checkbetting!=1 && checkbetting!=2)	
					{
						out.println("<script type='text/javascript'>"
						+ "alert('������ ������ Ĩ���� ���ų� ũ�� �����ؾ��մϴ�.\n �ٽ� �Է����ּ���');history.back();</script>");
						player1.wrongBetchips(); 				// �߸� ���������Ƿ� Ĩ ���󺹱�
					}
				
					else // -������ �ٺ����� �����ߴ��� üũ
					{
						if(checkbetting==1)				// ���� �ͺ��� ���� ������ ��� 
						{
							out.println("<script type='text/javascript'>"
							+ "alert('���� Ĩ ������  �����ϴ�.\n �ٽ� �Է����ּ���');history.back();</script>");
						}
						else if(checkbetting==2) 		// ���� ������ ��� 
						{
							player1.changeLose(0);						// ���� ���� ǥ���ϱ�
							RoundResult(userOrder);						// ���� ��� Ȯ�� �Լ�	
						
							losemsg = userOrder +" �÷��̾ �����Ͽ����ϴ�.";
							winmsg = userOrder+1 +" �÷��̾ �¸��Ͽ����ϴ�.";
							showThings(request,response,0);				// ���� �޼����� ���
						}
						else if(checkbetting==3) 		// ������ ��� 
						{
							System.out.printf("1 �÷��̾ ������ Ĩ %d\n", player1.getBetchip());
							System.out.printf("1 �÷��̾��� ����Ĩ %d\n", player1.getChips());
							System.out.printf("1 ���� Ĩ %d\n", player1.getAccchip());
						
							userOrder++;	// �÷��̾� ���� ����
						
							if(dealer.checkSame(player1, player2)!=0)	// ���õ� Ĩ�� ���� ������ ���
							{
								RoundResult(userOrder);					// ���� ��� Ȯ�� �Լ�
								msg = "�������� �����ϼ̽��ϴ�.";
							}
							showThings(request,response,0);				// ���� �޼����� ���
						}
					}
				}
			
				else if(userOrder==2 && temp2.equals(saveid[1]))	// 2�� ������ id���� ������
				{	
					player2.setBetchip(temp);						// ������ ���� int�� ��ȯ
					int checkbetting = player2.betChips();			// ���� ���� ���θ� Ȯ�� (betChips�Լ� ����)
				
					// �߸������� ��� - ������ üũ���ִ� ���  //
					if(player2.getAccchip()<player1.getAccchip() && checkbetting!=1 && checkbetting!=2)
					{
						out.println("<script type='text/javascript'>"
						+ "alert('������ ������ Ĩ���� ���ų� ũ�� �����ؾ��մϴ�.\n �ٽ� �Է����ּ���');history.back();</script>");
						player2.wrongBetchips(); 				// �߸� ���������Ƿ� Ĩ ���󺹱�
					}
				
					else // -������ �ٺ����� ���þ��ߴ��� üũ
					{
						if(checkbetting==1) 			// ���� �ͺ��� ���� ������ ��� 
						{
							out.println("<script type='text/javascript'>"
							+ "alert('���� Ĩ ������  �����ϴ�.\n �ٽ� �Է����ּ���');history.back();</script>");
						}
						else if(checkbetting==2) 		// ���� ������ ��� 
						{
							player2.changeLose(0);						// ���� ���� ǥ���ϱ�
							RoundResult(userOrder);						// ���� ��� Ȯ�� �Լ�
						
							losemsg = userOrder+" �÷��̾ �����Ͽ����ϴ�.";
							winmsg = userOrder-1 +" �÷��̾ �¸��Ͽ����ϴ�.";
							showThings(request,response,0);				// ���� �޼����� ���
						}
						else if(checkbetting==3) 		// ������ ���  
						{
							System.out.printf("2 �÷��̾ ������ Ĩ %d\n", player2.getBetchip());
							System.out.printf("2 �÷��̾��� ����Ĩ %d\n", player2.getChips());
							System.out.printf("2 ���� Ĩ %d\n", player2.getAccchip());
						
							userOrder--;	// �÷��̾� ���� ����
						
							if(dealer.checkSame(player1, player2)!=0)	// ���õ� Ĩ�� ���� ������ ���
							{
								RoundResult(userOrder);					// ���� ��� Ȯ�� �Լ�
								msg = "�������� �����ϼ̽��ϴ�.";
							}
							showThings(request,response,1);				// ���� �޼����� ���
						}
					}
				}
				else out.println("<script type='text/javascript'>"	// ���� ���ʰ� �ƴҋ�	
					+ "alert('������ʰ��Ƴ�');history.back();</script>");
			}
			else out.println("<script type='text/javascript'>"	// �Է°� ���� ������ ��	
					+ "alert('�Է°��� �����ϴ�');history.back();</script>");
		}

				
	out.println("<html><head>");							// 3�ʸ��� GameServer ���ΰ�ħ
	out.println("<meta http-equiv='refresh'content = '3; url=GameServer'>");
	out.println("</head><body>");							
	out.println("<h3>���� ����</h3>");
	for(int i=0; i<2; i++) out.println(saveid[i]+"<br>");	// ��� �α��� Ȯ�� �޽��� ���
	out.println("</head><body>");
	out.println("</body></html>");
	out.close();	

	}
}
