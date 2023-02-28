package board;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import service.JDBCUtil;

public class BoardDAO {
	private Connection conn=null;
	private PreparedStatement pstmt = null;
	private ResultSet rs =null;
	
	private static BoardDAO dao = null;
	private BoardDAO() {}
	public static BoardDAO getInstance() {
		if(dao==null)dao=new BoardDAO();
		return dao;
	}
	//NOTICEBOARD 안에 있는 모든 레코드를 읽어서 ArrayList에 담아 변환하는 메소드
	public ArrayList<BoardVO> selectAll () {
		ArrayList<BoardVO> list =null;
		//커넥션 풀에 연결 : 연결객체를 반환
		conn = JDBCUtil.getConnection();
		String sql = "select num, writer, subject, email, content, password, reg_date, readcount from NOTICEBOARD order by num asc";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {//데이터가 있으면,
				list = new ArrayList<>();//ArrayList 객체 생성
				do {
					//int num, String writer, String subject, String email, String content, String password, Date reg_date,int readcount
					// num, writer, subject, email, content, password, reg_date, readcount
				list.add(new BoardVO(rs.getInt("num"),rs.getString("writer"),
						rs.getString("subject"),rs.getString("email"),rs.getString("content"),rs.getString("password"),
						rs.getDate("reg_date"),rs.getInt("readcount")
						));
				}while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return list;
	}
	
	public BoardVO selectBoard(int bno) {
		BoardVO vo = null;
		//커넥션 풀에 연결 : 연결객체를 반환
		conn = JDBCUtil.getConnection();
		String sql = "select * from NOTICEBOARD where num =?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			if(rs.next()) {//검색한 책이 있으면
				vo=new BoardVO(rs.getInt("num"),rs.getString("writer"),
						rs.getString("subject"),rs.getString("email"),rs.getString("content"),rs.getString("password"),
						rs.getDate("reg_date"),rs.getInt("readcount"));
//				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return vo;
	}
	//페이지를 위한 메소드 시작
	//row값, 끝 row 값을 전달받아서 해당 행을 읽어서 ArrayList에 담아 반환해주는 메소드 생성
	public ArrayList<BoardVO> selectBoard(int begin, int end){
		ArrayList<BoardVO> list = null;
		conn = JDBCUtil.getConnection();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from (");
		sql.append("select rownum rn,A.* from");
		sql.append(" (select *  from NOTICEBOARD order by num desc) A ");
		sql.append(" where rownum <= ?) "); // ? 행의 마지막 값
		sql.append("where rn>=?"); // ? 행의 첫번째 값
				
//"select * from ( select rownum rn,A.* from (select *  from NOTICEBOARD order by bno desc) A where rownum <= 20) where rn>10";
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, end);
			pstmt.setInt(2, begin);
			rs = pstmt.executeQuery();
			if(rs.next()) {//데이터가 있으면,
				list = new ArrayList<>();//ArrayList 객체 생성
				do {
				list.add(new BoardVO(rs.getInt("num"),rs.getString("writer"),
						rs.getString("subject"),rs.getString("email"),rs.getString("content"),rs.getString("password"),
						rs.getDate("reg_date"),rs.getInt("readcount")
						));
				}while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return list;
	}
	
	//NOTICEBOARD 의 전체 행의 수 반환
  	public int getTotalRow(String tableName) {
  		int result=0;
  		conn = JDBCUtil.getConnection();
  		String sql = "select count(*) from " + tableName;
  		
  		try {
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())result=rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
  		return result;
  	}
	
  	//삽입 메소드
  	public int insertBoard(int num, String writer, String subject, String email, String content, String password) {
  		int r = 0;
  		conn = JDBCUtil.getConnection();
  		String sql = "insert into NOTICEBOARD (num, writer, subject, email, content, password, reg_date) values (NUM_SEQ.nextval,?,?,?,?,?,sysdate)";
  		
  		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, writer);
			pstmt.setString(2, subject);
			pstmt.setString(3, email);
			pstmt.setString(4, content);
			pstmt.setString(5, password);
//			pstmt.setInt(6, readcount);
			r=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt, conn);
		}
  		return r;
  	}
	
  	
  	//삭제 메소드 구현 bno 받아서
  	public int deleteBoard(int num) {
        int result = 0;
        conn = JDBCUtil.getConnection();
        
        String sql = "delete from NOTICEBOARD where num = ?";
        try {
           pstmt = conn.prepareStatement(sql);
           pstmt.setInt(1, num);
           result = pstmt.executeUpdate();
        } catch (SQLException e) {
           e.printStackTrace();
        }finally {
           JDBCUtil.close(pstmt, conn);
        }
        return result;
     }
  	
  	public int BoardUpdate(int num, String subject, String email, String content, String password) {
        int result = 0;
        conn = JDBCUtil.getConnection();
        
        String sql = "update NOTICEBOARD set subject=?, email=?, content=?, password=? where num=?";
        try {
           pstmt = conn.prepareStatement(sql);
           
           pstmt.setString(1, subject);
           pstmt.setString(2, email);
           pstmt.setString(3, content);
           pstmt.setString(4, password);
           pstmt.setInt(5, num);
           
           result = pstmt.executeUpdate();
        } catch (SQLException e) {
           e.printStackTrace();
        }finally {
           JDBCUtil.close(pstmt, conn);
        }
        return result;
     }
  	
  	public int readcountPlus(int num) {
  		int result=0;
  		conn=JDBCUtil.getConnection();
  		String sql = "update noticeboard set readcount=readcount+1 where num=?";
  		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
	           JDBCUtil.close(pstmt, conn);
	        }
  		return result;
  	}
  	
  	
  	
  	//row값, 끝 row 값을 전달받아서 컬럼(title, writer, content)의 키워드 값이 있는 행을 반환
  	public ArrayList<BoardVO> selectBoard(int begin, int end, 
  			String columnName, String keyword){
		ArrayList<BoardVO> list = null;
		conn = JDBCUtil.getConnection();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from (");
		sql.append("select rownum rn,A.* from");
		sql.append(" (select *  from NOTICEBOARD where ");
		sql.append(columnName);
		sql.append(" like ? order by num desc) A ");
		sql.append(" where rownum <= ?) "); // ? 행의 마지막 값
		sql.append("where rn>=?"); // ? 행의 첫번째 값
//"select * from ( select rownum rn,A.* from (select *  from NOTICEBOARD order by bno desc) A where rownum <= 20) where rn>10";
		
		
		System.out.println(sql.toString());
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setInt(2, end);
			pstmt.setInt(3, begin);
			rs = pstmt.executeQuery();
			if(rs.next()) {//데이터가 있으면,
				list = new ArrayList<>();//ArrayList 객체 생성
				do {
				list.add(new BoardVO(rs.getInt("num"),rs.getString("writer"),
						rs.getString("subject"),rs.getString("email"),rs.getString("content"),rs.getString("password"),
						rs.getDate("reg_date"),rs.getInt("readcount")
						));
				}while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return list;
	}
  	
  	//테이블의 특정 컬럼에 특정 키워드가 있는 행의 개수 구하기
  	public int getTotalRow(String tableName, String columnName, String keyword) {
  		int result=0;
  		 conn = JDBCUtil.getConnection();
  		String sql = "select count(*) from " + tableName + " where"+" like ?";
  		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%"); 
			rs=pstmt.executeQuery();
			if(rs.next())result=rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
  		return result;
  	}
  	
  	
	
	
	
	
	
	
}
