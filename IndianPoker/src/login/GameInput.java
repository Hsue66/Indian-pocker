package login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.JavaFileManager.Location;

/**
 * Servlet implementation class GameInput
 */
@WebServlet("/GameInput")
public class GameInput extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	String playerId[];	// ID 값 저장
	int index = 0;
	public void init(){	// 배열 초기화
		playerId = new String[2];
		for(int i=0 ; i<2 ; i++){
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
		request.setCharacterEncoding("UTF-8");	// 인코딩
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String num = request.getParameter("num");	// num, id 값 받아오기
		String id = request.getParameter("id");
		
		out.println("<html><body>");
		out.println("<form method=get action=GameResult>");	// GameResult.java 에 get방식 전송
		if (num != null) {
			synchronized (playerId) {
				playerId[index] = id;
				out.println("<input type=hidden name =num value="+num+">");
				out.println("<input type=hidden name=id value="+playerId[index]+">");
				index = (index+1 ) % 2;
			}
		}
		
		out.println("<input type=submit value='결과 확인'>");
		out.println("</body></html>");		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}