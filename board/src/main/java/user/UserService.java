package user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import comment.ScoreDAO;


public class UserService {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String viewPath="/WEB-INF/jsp/user";
	
	
	public UserService(HttpServletRequest request, HttpServletResponse response) {
		this.request=request;this.response=response;
		
	}

	public String exec() {
		String cmd=request.getParameter("cmd");
		//System.out.println("cmd-"+cmd);
		if(cmd.equals("loginpage")) {
			return viewPath+"/login.jsp";
		}else if(cmd.equals("login")) {
			return login();
		}else if(cmd.equals("joinpage")) {
			return viewPath+"/join.jsp";
		}else if(cmd.equals("join")) {
			insertUser(); return "/board?cmd=list"; 
		}else if(cmd.equals("logout")) {
			HttpSession session = request.getSession();
			session.removeAttribute("vo");
			
			return "/board?cmd=list";
		}
		return null;
	}

	private String login() {
		String userid = request.getParameter("userid");
		String userPwd = request.getParameter("userPwd");
		UserDAO dao = UserDAO.getInstance();
		UserVO vo = dao.login(userid, userPwd);
		if(vo!=null) {
			//세션 객체 얻어오기
			HttpSession session = request.getSession();
			System.out.println(session.getId());
			session.setAttribute("vo", vo);
			return "/board?cmd=list";
			
		}else {
			request.setAttribute("message", "로그인 실패 : 아이디 / 패스워드가 다릅니다.");
			return viewPath+"/login.jsp";
		}
		
		
	}
	//String userid, String userPwd, String userName, String userGender, String userEmail,int admin
	private void insertUser() {
		String userid=request.getParameter("userid");
		if(userid!=null)userid=userid.trim();
		String userPwd=request.getParameter("userPwd");
		if(userPwd!=null)userPwd=userPwd.trim();
		String userName=request.getParameter("userName");
		if(userName!=null)userName=userName.trim();
		String userGender=request.getParameter("userGender");
		if(userGender!=null)userGender=userGender.trim();
		String userEmail=request.getParameter("userEmail");
		if(userEmail!=null)userEmail=userEmail.trim();
		
		UserDAO dao = UserDAO.getInstance();
		dao.insertUser(userid, userPwd, userName, userGender, userEmail);
	}
	
	
	

}
