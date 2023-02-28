package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import service.JDBCUtil;


public class UserDAO {
	private Connection conn=null;
	private PreparedStatement pstmt = null;
	private ResultSet rs =null;
	
	
	private static UserDAO dao=null;
	private UserDAO() {}
	public static UserDAO getInstance() {
		if(dao==null)dao = new UserDAO();
		return dao;
	}
	//String userId, String userPwd, String userName, String userGender, String userEmail
	//userId,userPwd,userName,userGender,userEmail
	public ArrayList<UserVO> selectAll(){
		ArrayList<UserVO> list = null;
		conn = JDBCUtil.getConnection();
		String sql = "select * from usertbl";
		try {
			pstmt=conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next())list = new ArrayList<>();
			do {
			list.add(new UserVO(rs.getString(1)
					,rs.getString(2),rs.getString(3),rs.getString(4),
					rs.getString(5),rs.getInt(6)));
			}while(rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		return list;
	}
	
	public UserVO login(String userid,String userPwd) {
		UserVO vo = null;
		conn = JDBCUtil.getConnection();
		String sql = "select username, admin from usertbl where userid = ? and userPwd =? ";
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userid);pstmt.setString(2, userPwd);
			rs=pstmt.executeQuery();
			if(rs.next()) {//데이터 존재 : 로그인 성공
				vo=new UserVO(userid, rs.getString("userName"),rs.getInt("admin"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, pstmt, conn);
		}
		
		
		return vo;
	}
	
	//String userid, String userPwd, String userName, String userGender, String userEmail,int admin
	public int insertUser(String userid, String userPwd, String userName,
			String userGender, String userEmail) {
		int result = 0;
		conn=JDBCUtil.getConnection();
		String sql = "insert into usertbl (userid,userPwd,userName,userGender,userEmail) values (?,?,?,?,?) ";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, userPwd);
			pstmt.setString(3, userName);
			pstmt.setString(4, userGender);
			pstmt.setString(5, userEmail);
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt, conn);
		}
		
		
		
		
		
		
		
		
		
		
		
		return result;
	}
	
}
