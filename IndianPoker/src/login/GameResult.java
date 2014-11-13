package login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class result1
 */
@WebServlet("/GameResult")
public class GameResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	int playerNum[];
	String playerId[];
	int index = 0;
	int check = 0;
	String Winner="";
	public void init(){
		playerNum = new int[2];
		playerId = new String[2];
		for(int i=0; i<2 ; i++){
			playerNum[i] = 0;
			playerId[i]="";
		}
	}
    public GameResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String num = request.getParameter("num");
		String id = request.getParameter("id");
		if (num != null) {
			synchronized (playerNum) {
				playerNum[index] = Integer.parseInt(num);
				playerId[index] = id;
				index = (index + 1) % 2;
				check++;
			}
		}
		if(check == 2)
		{
			if(playerNum[0] > playerNum[1])
				Winner = playerId[0];
			else if(playerNum[0] < playerNum[1])
				Winner = playerId[1];	
		}	
		PrintWriter out = response.getWriter();
		out.println("<html><head>");
		out.println("<meta http-equiv='refresh' ");
		out.println("content = '2; url=GameResult'>");
		out.println("</head>");
		out.println("<body>");
		int i=index;
		while(true){
			if (playerId[i] != "") {
				out.print(playerId[i] + " : ");
				out.print(playerNum[i]);
				out.println("<br>");
			}
			i = ++i%2;
			if(i==index-1)
				break;
			if(index == 0 && i == 1)
				break;
		}
		if(playerId[i] != "" && Winner != "")
		{
			out.print(playerId[i]+" : ");
			out.print(playerNum[i]);
			out.println("<br>");
			out.print(Winner+"님이 승리하셨습니다.");
			out.println("<br>");
		}
		out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
