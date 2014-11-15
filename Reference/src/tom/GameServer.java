package tom;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**Servlet implementation class GameServer**/

public class GameServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// int userOrder[];				// player���� array		�ε���0 => 1���÷��̾�
	int index = 0;					// �ε��� ����
	
	String loginConfirm[];			// �α��� Ȯ�� �޽����� ������ �迭
		
	public void init() {				// �ʱ�ȭ �Լ�
		
		//userOrder = new int[2];			// 2���� ������ ���� array �Ҵ�
		loginConfirm = new String[2];	// ��2���� �α��� Ȯ�� �޽����� ������ array �Ҵ�
		
		for(int i=0; i<2; i++) {
			//userOrder[i] = 0;			// int array�� 0���� �ʱ�ȭ
			loginConfirm[i] = "";		// String array�� null�� �ʱ�ȭ	
			
		}
	}
		
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("KSC5601");
		response.setContentType("text/html;charset=KSC5601");			// ���ڵ� ����.....
		PrintWriter out = response.getWriter();
			
		String userid = request.getParameter("userid");			// user�� id�� �޾ƿͼ�
		if(userid!=null) {										// id�� null�̾ƴ϶�� (�翬�� �ƴϰ�����..)
			synchronized (loginConfirm) {							// �ѹ��� �Ѹ�
				loginConfirm[index] = userid +"���� �����ϼ̽��ϴ�.";		// �α��� Ȯ�� �޽��� ����	
				if(loginConfirm[0].equals(loginConfirm[1])) {		// �� ������ �г����� ��ġ��
					loginConfirm[index] = "";						// ������ Ȯ�� �޽����� �ٽ� �����
					response.sendRedirect("loginError.jsp");		// ������������ ����
					index=(index+1) % 2;							// �ε��� ����..	
				}
				index=(index+1) % 2;							// �ε����� 1�� ��������
			}
		}
			
		out.println("<html><head>");							// 2�ʸ��� GameServer ���ΰ�ħ
		out.println("<meta http-equiv='refresh'content = '2; url=GameServer'>");
		out.println("</head><body>");							
		
		out.println("<h3>���� ����</h3>");
		for(int i=0; i<2; i++) out.println(loginConfirm[i]+"<br>");	// ��� �α��� Ȯ�� �޽��� ���
		
		
		
		out.println("</body></html>");
		out.close();
	}
}
