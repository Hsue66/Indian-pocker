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
	
	int y1, y2, y3, y4, y5;					// �׳��ʿ��Ҷ� ������ ���� ����...
	
	int stage;
	int die;
	
	Dealer dealer = new Dealer();
	Players player1 = new Players();			
	Players player2 = new Players();
	
	public void init() {				// �ʱ�ȭ �Լ�
		player1.Init(1);				// 
		player2.Init(2);
		
		y1=0;		// ������ ������ ���� ���� �ʱ�ȭ
		y2=0;		// ������ ������ ���� ���� �ʱ�ȭ
		
		stage=0;
		die=0;
		index = 0;
		
		userOrder = 2;						// 2��°�α����ϴ� ���� ���� �ڵ忡 �����ϹǷ�... 
		saveid = new String[2];
		for(int i=0; i<2; i++) saveid[i] = "";
	}
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("KSC5601");
		response.setContentType("text/html;charset=KSC5601");			// ���ڵ� ����.....
		PrintWriter out = response.getWriter();
	////////////////////////////////////////////////////////////////////////////////////////////////		
		
		String userid = request.getParameter("userid");			// user�� id�� �޾ƿͼ�
		if(userid!=null) {										// id�� null�̾ƴ϶�� (�翬�� �ƴϰ�����..)
			synchronized (saveid) {							// �ѹ��� �Ѹ�
				saveid[index] = userid;		
				index=(index+1) % 2;
			}
		}	
			
		if(saveid[1]!="") {				// 2���� �α����ߴٸ�!!!!!!
				if(y1<1){				// �̺κ��� �ѹ��� �ݺ��ǵ��� ����..
						stage++;
						//System.out.printf("<%d���带 �����մϴ�>\n",stage);
						if(stage%10==1)	dealer.initCardDeck();			// ī�嵦�����
						dealer.distributeCard(player1, player2);		// �÷��̾�� ī��̾��ְ�
						dealer.initRound(player1, player2);				// �⺻������ ���ش�
						y1++;
				}
				
				if(y2<2) {				// �̺κ��� 2���� �ݺ��ǵ��� ����..
					if(userOrder==1) {			
																		// 2������ ������ 1���� ����
						request.setAttribute("mycard", Dealer.card1);
						request.setAttribute("enemycard", Dealer.card2);
						request.setAttribute("enemyid", saveid[1]);
						request.setAttribute("myleftchip", player1.getChips());
						request.setAttribute("enemyleftchip", player2.getChips());		// ������� 1���÷��̾ jsp�� �̵�
						request.getRequestDispatcher("playeroutput.jsp").forward(request, response);
						y2++;			// ���̻� �� �ڵ忣 �ȵ��ð���.. �Ƹ� 2��° ���� �� �ٽ� �ü���...
						
					}
					else if(userOrder==2) {					// %%%���������2�̹Ƿ� ������� ����%%%%
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
	
		
		if(temp3!=null) {				// '��뺣��Ȯ��'�� ������ �Դٸ� !!
			if(userOrder==2 && temp2.equals(saveid[1])) {		// ��������� 2�� �÷��̾��̰� �׳� id���� �´ٸ�
				request.setAttribute("myleftchip", player2.getChips());	// ���� ����Ĩ��
				request.setAttribute("enemyleftchip", player1.getChips());	// ������ ����Ĩ��
				request.setAttribute("enemybetchip", player1.getBetchip());	// ������ ������ Ĩ ������ ���
				request.getRequestDispatcher("playeroutput.jsp").forward(request, response);	// �ٽ� jsp�� �̵�
			}
			////***************************************************************************////
			else {		// �� ���� ���
				request.setAttribute("myleftchip", player1.getChips());	// ���� ����Ĩ��
				request.setAttribute("enemyleftchip", player2.getChips());	// ������ ����Ĩ��
				request.setAttribute("enemybetchip", player2.getBetchip());	// ������ ������ Ĩ ������ ���
				request.getRequestDispatcher("playeroutput.jsp").forward(request, response);	// �ٽ� jsp�� �̵�
			}
			////***************************************************************************////
		}
		
		if(temp!=null && temp2!=null)	// ������ �ߴٸ�
		{	
			if(userOrder==1 && temp2.equals(saveid[0]))		// 1�� ������ id���� ������
			{	
				player1.setBetchip(temp);					// ������ ���� int�� ��ȯ
				int checkbetting = player1.betChips();			// ���� ���� ���θ� Ȯ�� (betChips�Լ� ����)
				if(checkbetting==1) {							
					out.println("<script type='text/javascript'>"
						+ "alert('���� Ĩ ������  �����ϴ�. �ٽ� �Է����ּ���');history.back();</script>");
				}
				else if(checkbetting==2) {
					out.println("<script type='text/javascript'>"
						+ "alert('0���� �����Ͻ� �� �����ϴ�. �ٽ� �Է����ּ���');history.back();</script>");
				}
				else if(checkbetting==3) {
					System.out.printf("�÷��̾ ������ Ĩ %d\n", player1.getBetchip());
					System.out.printf("�÷��̾��� ����Ĩ %d\n", player1.getChips());
					System.out.printf("���� Ĩ %d\n", player1.getAccchip());
					userOrder++;					// 1���÷��̾ ����� �����ϸ� 1���÷��̾�� �������� ���߰� ���� �ѱ�
					
				}
				
			}
			else if(userOrder==2 && temp2.equals(saveid[1]))	// 2�� ������ id���� ������
			{	
				player2.setBetchip(temp);
				int checkbetting = player2.betChips();
				if(checkbetting==1) {
					out.println("<script type='text/javascript'>"
						+ "alert('���� Ĩ ������  �����ϴ�. �ٽ� �Է����ּ���');history.back();</script>");
				}
				else if(checkbetting==2) {
					out.println("<script type='text/javascript'>"
						+ "alert('0���� �����Ͻ� �� �����ϴ�. �ٽ� �Է����ּ���');history.back();</script>");
				}
				else if(checkbetting==3) {
					System.out.printf("�÷��̾ ������ Ĩ %d\n", player2.getBetchip());
					System.out.printf("�÷��̾��� ����Ĩ %d\n", player2.getChips());
					System.out.printf("���� Ĩ %d\n", player2.getAccchip());
					//userOrder++;				// 2�� �÷��̾ �������� ���߰�  &&&&&&&&& �ϴ� ���⼭ ��!!&&&&&&
					request.getRequestDispatcher("playeroutput.jsp").forward(request, response);	// �ٽ� jsp�� �̵�
				}
			}
			else out.println("<script type='text/javascript'>"	// ���� ���ʰ� �ƴҋ�
					+ "alert('������ʰ��Ƴ�');history.back();</script>");
			}
		
		
		out.println("1�� �÷��̾ ������ Ĩ: "+player1.getBetchip()+"<br>");
		out.println("1�� �÷��̾��� ����Ĩ: "+player1.getChips()+"<br>");
		out.println("1�� �÷��̾��� ���� Ĩ : "+player1.getAccchip()+"<br>");
		out.println("2�� �÷��̾ ������ Ĩ : "+player2.getBetchip()+"<br>");
		out.println("2�� �÷��̾��� ����Ĩ : "+player2.getChips()+"<br>");
		out.println("2�� �÷��̾��� ���� Ĩ : "+player2.getAccchip()+"<br>");
		
			
				
	out.println("<html><head>");							// 2�ʸ��� GameServer ���ΰ�ħ
	out.println("<meta http-equiv='refresh'content = '10; url=GameServer'>");
	out.println("</head><body>");							
	out.println("<h3>���� ����</h3>");
	for(int i=0; i<2; i++) out.println(saveid[i]+"<br>");	// ��� �α��� Ȯ�� �޽��� ���
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
