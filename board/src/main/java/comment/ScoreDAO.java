package comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import service.JDBCUtil;


public class ScoreDAO {
	private Connection conn=null;
	private PreparedStatement pstmt = null;
	private ResultSet rs =null;
	
	private static ScoreDAO dao=null;
	private ScoreDAO() {}
	public static ScoreDAO getInstance() {
		if(dao==null)dao=new ScoreDAO();
		return dao;
	}
	
	public double getboardAvg(int num) {
		double avg = 0;
		conn = JDBCUtil.getConnection();
		String sql = "select avg(score) from boardscore where num =?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs= pstmt.executeQuery();
			
			if(rs.next()) {
				avg=rs.getDouble("avg(score)");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
				
		
		
		return avg;
	}
	public int insertCmt( int num, String userid, int score, String cmt) {
		int result =0;
		conn = JDBCUtil.getConnection();
		String sql = "insert into boardscore (sno,num,userid,score,cmt) values (sno_seq.nextval,?,?,?,?)";
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, userid);
			pstmt.setInt(3, score);
			pstmt.setString(4, cmt);
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt, conn);
		}
		return result;
	}
	
	// 도서 번호에 해당하는 별점의 총 개수 구하기
		public int getnumCnt(int num) {
			int result=0;
			conn = JDBCUtil.getConnection();
			String sql = "select count(*) from boardscore where num=?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					result=rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JDBCUtil.close(rs, pstmt, conn);
			}
			return result;
		}
		
		// 시작 행과 마지막 행을 받아 ArrayList에 담아서 반환
		public ArrayList<ScoreVO> select(int begin , int end, int num){
			ArrayList<ScoreVO> list =null;
			conn = JDBCUtil.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select * from (");
			sql.append("select rownum rn,A.* from");
			sql.append(" (select userid,score,cmt  from boardscore where num=? order by sno desc) A ");
			sql.append(" where rownum <= ?) "); // ? 행의 마지막 값
			sql.append("where rn>=?"); // ? 행의 첫번째 값
			
			try {
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setInt(1, num);
				pstmt.setInt(2, end);
				pstmt.setInt(3, begin);
				rs=pstmt.executeQuery();
				if(rs.next()) {
					list=new ArrayList<>();
					do {
						list.add(new ScoreVO(rs.getString("userid"),
								rs.getDouble("score"),rs.getString("cmt")));
					}while(rs.next());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				JDBCUtil.close(rs, pstmt, conn);
			}
			
			return list;
		}
		
		//도서번호로 평점 행 지우기
		public int delRows(int num) {
			int r=0;
			conn=JDBCUtil.getConnection();
			String sql = "delete from boardscore where num=?";
			try {
				pstmt=conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				r=pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBCUtil.close(pstmt, conn);
			}
			return r;
			//r 빼고 int 대신 void 해도 됨.
		}
	
}
