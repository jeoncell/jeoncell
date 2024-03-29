package board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BookControllerServlet
 */
@WebServlet("/board")
public class BoardControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//encoding
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//서비스객체 생성 : 수행메소드 실행
		String view = new BoardService(request,response).exec();
//		RequestDispatcher rd = request.getRequestDispatcher(view); 
//		rd.forward(request, response);
		
		if(view!=null)
		request.getRequestDispatcher(view).forward(request, response);
		
		
	}

}
