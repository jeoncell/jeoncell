package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user")
public class UserControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserControllerServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String view=new UserService(request, response).exec();
		if(view!=null)
		request.getRequestDispatcher(view).forward(request, response);
		
	}

}
