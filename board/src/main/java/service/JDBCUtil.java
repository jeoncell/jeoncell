package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JDBCUtil {
	//Connection pool에 연결하는 연결객체를 생성하여 반환하는 메소드 생성
	//오라클로 접속하기위해 이름을 설정하고 정의한 부분이 3곳 있다.
	//1. Server의 context.xml
	//2. 내 웹프로젝트의 web.xml
	//3. Connection 객체 얻어낼 때
	//위 3가지 이름이 반드시 일치 해야한다. (대소문자 구분)
	
	public static Connection getConnection(){
		Connection conn=null;
		try {
			Context ctx = new InitialContext();
			Context envContext=(Context)ctx.lookup("java:/comp/env");
			DataSource ds=(DataSource)envContext.lookup("jdbc/javaAAOracle");
			//web.xml 4737번째 줄과 같아야함.
			//<res-ref-name>  jdbc/javaDBOracle  </res-ref-name> //리소스-레퍼런스-네임
			conn=ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(PreparedStatement pstmt, Connection conn) {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public static void close(ResultSet rs,PreparedStatement pstmt, Connection conn) {
		try {
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
	
	
}
