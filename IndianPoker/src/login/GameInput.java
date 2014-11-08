package login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GameInput
 */
@WebServlet("/GameInput")
public class GameInput extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	int playerNum[];
	String playerId[];
	int index = 0;
	public void init(){
		playerNum = new int[2];
		playerId = new String[2];
		for(int i=0 ; i<2 ; i++){
			playerNum[i]=0;
			playerId[i]="";
		}
	}
    public GameInput() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String num = request.getParameter("num");
		String id = request.getParameter("id");
		System.out.println(id);
		synchronized (playerNum) {
			playerNum[index] = Integer.parseInt(num);
			playerId[index] = id;
			index = (index + 1) % 2;
		}
		System.out.println(index);
		out.println("<html><body>");
		if (index == 0) {
			if (playerNum[0] > playerNum[1]) {
				out.print("<p align = center>" + playerId[0]
						+ "님이 승리하셨습니다. </p");
			}
			if (playerNum[0] < playerNum[1]) {
				out.print("<p align = center>" + playerId[1]
						+ "님이 승리하셨습니다. </p");
			}
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
