package board;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.ScoreDAO;
import comment.ScoreVO;
import service.PageVO;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


public class BoardService {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String viewPath="/WEB-INF/jsp/board";
	
	
	public BoardService(HttpServletRequest request, HttpServletResponse response) {
		this.request=request;this.response=response;
		
		
	}

	public String exec() {
		String cmd=request.getParameter("cmd");
		if(cmd==null||cmd.equals("list")) {
			getList();
			return viewPath+"/board_list.jsp";
		}else if(cmd.equals("view")) {
			getBoard();
			return viewPath+"/board_view.jsp";
		}else if(cmd.equals("insertpage")) {
			return viewPath+"/board_create.jsp";
		}else if(cmd.equals("insert")) {
			insertBoard();
			return "/board?cmd=list";
		}else if(cmd.equals("del")) {
			deleteBoard();
			return "/board?cmd=list";
		}else if(cmd.equals("update")) {
			updateBoard();
			return viewPath+"/board_view.jsp";
		}else if(cmd.equals("cmt")) {
			insertScore();
		}else if(cmd.equals("cmtlist")) {
			listScore();
		}
		
		
		
		
		return null;
	}
	
	
	private void listScore() {
		System.out.println("screList.do ====접속완료.");
		int num=1,page=1;
		String strno = request.getParameter("num");
		if(strno!=null)
		num=Integer.parseInt(strno);
		
		strno = request.getParameter("page");
		if(strno!=null)
		page=Integer.parseInt(strno);
		
		//등록된 도서번호별 총 개수 구하기
		ScoreDAO dao = ScoreDAO.getInstance();
		int totalCount = dao.getnumCnt(num);
		int displayRow = 5; //한번에 보고싶은 리스트 수

		//paging처리
		//int page, int totalCount, int displayRow, int displayPage) {
		PageVO pvo = new PageVO(page,totalCount,displayRow,1);//무조건 1페이지씩 보는것.
		boolean next = pvo.pagingStar();
		
		// list 받기
		int endRow=page*displayRow;
		List<ScoreVO> list = dao.select(endRow-displayRow+1, endRow, num);
		System.out.println("num"+num);
		System.out.println("page"+page);
		System.out.println("list"+list);
		//보내줄 데이터를 받아서 사용하기 좋은 형태로 조립해서 전송
		StringBuilder sb= null;
		if(list!=null) {
			sb=new StringBuilder();
			sb.append("{");
			if(next)sb.append("'next':'true'}!@");
			else sb.append("'next':'false'}!@");// !@ -> 받는측에서 잘라서 사용
			for(ScoreVO vo:list) {
				sb.append("{");
				sb.append("'score':'"+vo.getScore()+"',");
				sb.append("'userid':'"+vo.getUserid()+"',");
				sb.append("'cmt':'"+vo.getCmt()+"'}!@");
			}//for
			System.out.println(sb.toString());
		}
		String result="err";
		if(sb!=null) {//보낼 데이터가 있다.
			result=sb.substring(0,sb.length()-2);//마지막 !@ 제거
		}
		//보내기 전 encoding (response에서 encoding)
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out =null;
		try {
			out = response.getWriter();
			out.print(result);//보내기
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void insertScore() {
		String strnum=request.getParameter("num");
		int num=1;
		if(strnum!=null)
		num=Integer.parseInt(strnum);
		
		String strscore=request.getParameter("score");
		int score=0;
		if(strscore!=null)
		score=Integer.parseInt(strscore);
		
		String userid=request.getParameter("userid");
		String cmt=request.getParameter("cmt");
		
		ScoreDAO dao = ScoreDAO.getInstance();
		dao.insertCmt(num, userid, score, cmt);
	}

	private void updateBoard() {
				String strnum=request.getParameter("num");
				int num=0;
				if(strnum!=null)num=Integer.parseInt(strnum);
				System.out.println("num="+num);
				//num, writer, subject, email, content, password, reg_date, readcount
				String subject = request.getParameter("subject");
				if(subject!=null)subject=subject.trim();
				String email = request.getParameter("email");
				if(email!=null)email=email.trim();
				String content = request.getParameter("content");
				if(content!=null)content=content.trim();
				String password = request.getParameter("password");
				if(password!=null)password=password.trim();
				//DB 저 장
				BoardDAO dao= BoardDAO.getInstance();
				dao.BoardUpdate(num, subject, email, content, password);
				
				BoardVO vo =dao.selectBoard(num);
				request.setAttribute("v", vo);
				
	}
	
	private void updateReadcount() {
		String strnum=request.getParameter("num");
		int num=0;
		if(strnum!=null)num=Integer.parseInt(strnum);
			BoardDAO dao= BoardDAO.getInstance();
			dao.readcountPlus(num);
			BoardVO vo =dao.selectBoard(num);
			request.setAttribute("v", vo);
	}

	private void deleteBoard() {
		int num = Integer.parseInt(request.getParameter("num"));
		BoardDAO dao =BoardDAO.getInstance();
		//도서와 관련된 도서평점 지우기.
		ScoreDAO sdao=ScoreDAO.getInstance();
		int r= sdao.delRows(num);//평점지우기
		System.out.println("r"+r);
		dao.deleteBoard(num);//도서 삭제.
	}
	
	
	private void insertBoard() {
		ServletContext context = request.getServletContext();
		String uploadPath = context.getRealPath("upload\\cover\\img");
//		String uploadPath = request.getServletContext().getRealPath("upload\\cover\\img");
		File file = new File(uploadPath);
		if(!file.exists())file.mkdirs();//업로드 디렉토리가 없으면 만들어 준다. 1번만하고 주석처리
		
		//new MultipartRequest : 업로드 파일이 있으면 업로드 완료된다.
		MultipartRequest mr = null;
		
		try {
			mr = new MultipartRequest(request, uploadPath, 10*1024*1024, "utf-8",new DefaultFileRenamePolicy());
			
			String strnum=mr.getParameter("num");;
			int num=1;
			if(strnum!=null)num =Integer.parseInt(strnum);
			
			String writer = mr.getParameter("writer");//userid
			
			String subject = mr.getParameter("subject");
			if(subject!=null)subject=subject.trim();
			String email = mr.getParameter("email");
			if(writer!=null)writer=writer.trim();
			String content = mr.getParameter("content");
			if(content!=null)content=content.trim();
			String password = mr.getParameter("password");
			if(password!=null)password=password.trim();
			
			//저장된 파일명 가져오기
			String saveFilename= mr.getFilesystemName("file");
			//num, writer, subject, email, content, password, reg_date, readcount
			//실제 파일명 가져오기
			String originFilename=mr.getOriginalFileName("file");
			
			BoardDAO dao = BoardDAO.getInstance();
			dao.insertBoard(num, writer, subject, email, content, password);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		//넘어오는 파라메터 값으로 데이터베이스에 한 행을 추가 -> 리스트 이동
	}

	private void getBoard() {
		String strnum=request.getParameter("num");
		int num=0;
		if(strnum!=null)
		num=Integer.parseInt(strnum);
		//num select 수행 결과를 vo 저장
		BoardDAO dao=BoardDAO.getInstance();
		BoardVO vo=dao.selectBoard(num);
		//request 객체 결과 vo 를 저장
		request.setAttribute("v", vo);//브라우저 request 객체에서 찾을때 이름은 v  v.code
		//Board_View.jsp 이동 forward 방식으로
		//이 도서의 평균평점 가져오기
		ScoreDAO sdao=ScoreDAO.getInstance();
		double avg=sdao.getboardAvg(num);
		request.setAttribute("avg", avg);
	}

	private void getList() {
		BoardDAO dao = BoardDAO.getInstance();
		//ArrayList<BoardVO> list = dao.selectAll();//전체 행 다 가져오는
		ArrayList<BoardVO> list = null;
		//displayRow, displayPage  전체 행과 현재페이지의 개수를 설정할 수 있다.
		int displayRow=5;
		int displayPage=5;
		//page 파라미터 받아오기
		int page = 0;//현재페이지 저장
		if(request.getParameter("page")!=null)//파라미터값이 들어오면
			page = Integer.parseInt(request.getParameter("page"));
		else page=1;//파라미터값이 넘어오지 않으면..
		//전체 행의 개수 구하기
		//테이블에서 출력할 행의 내용 가져오기
		//page 1 : 1~5, page 2 : 6~10, page 3 : 11~15
		int totalRow=0;
		int endRow=page*displayRow;
		int beginRow=endRow-displayRow+1;
		String keyword = request.getParameter("keyword");
		String columnName = null;
		
		
		String strnum=request.getParameter("num");
		int num=0;
		if(strnum!=null)num=Integer.parseInt(strnum);
			dao.readcountPlus(num);
			BoardVO vo =dao.selectBoard(num);
			request.setAttribute("v", vo);
		
			
		//Boardtbl의 전체 행의 수
		if(keyword==null||keyword.trim().isEmpty()) {//전체 테이블 검색
			totalRow=dao.getTotalRow("NOTICEBOARD");
			//키워드 검색 아니다.
			//keyword.trim().isEmpty() 참인경우
			keyword=null;
			list =dao.selectBoard(beginRow, endRow);
		}else {//keyword 검색
			columnName = request.getParameter("item");//title, writer, content 3개 중 1개 문자열 반환
			totalRow=dao.getTotalRow("NOTICEBOARD", columnName, keyword);
			list=dao.selectBoard(beginRow, endRow, columnName, keyword);
		}
		//public PageVO(int page, int totalCount, int displayRow, int displayPage)
		PageVO pageVo = new PageVO(page,totalRow,displayRow,displayPage);//PageVO 객체 생성
		//paging 메소드 호출 설정할 값 처리
		pageVo.paging();//계산에 의해서 설정하는 4가지 멤버변수에 값이 설정된다.
		pageVo.setColumnName(columnName);
		pageVo.setKeyword(keyword);//null or 문자열 들어올 수 있음.
		
	//list를 웹에서 접근 가능한 객체에 담아준다.
		request.setAttribute("list", list);
		request.setAttribute("pageVo", pageVo);
		
	}

}
