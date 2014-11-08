package login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GameJoin
 */
@WebServlet("/GameJoin")
public class GameJoin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	int index = 0;
	String message[];
	public void init(){
		message = new String[2];
		for(int i=0 ; i<2 ; i++){
			message[i]="";
		}
	}
    public GameJoin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String join = request.getParameter("id");
		if(join != null){
			String id = request.getParameter("id");
				synchronized (message) {
				message[index] ="<p align = center>" + id +" 님이 들어왔습니다.</p>";
				index=(index+1) % 2;
			}
		}
		PrintWriter out = response.getWriter();
		out.println("<html><head>");
		out.println("<meta http-equiv='refresh' ");
		out.println("content = '2; url=GameJoin'>");
		out.println("</head>");
		out.println("<body>");
		int i=index;
		while(true){
			out.print(message[i]);
			out.println("<br>");
			i = ++i%2;
			if(i==index-1)
				break;
			if(index == 0 && i == 1)
				break;
		}
		out.print(message[i]);
		out.println("<br>");
		out.println("</body></html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
