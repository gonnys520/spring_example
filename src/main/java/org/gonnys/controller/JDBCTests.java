package org.gonnys.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTests {

	@Test
	public void testConnection() throws Exception {
		log.info("test Connection Start.........................");

		// 사용하려는 데이터베이스명을 포함한 URL 기술
		String url = "jdbc:mysql://localhost:3306/jr?useSSL=false&serverTimezone=UTC";
		// 사용자 계정
		String id = "jr2018";
		// 사용자 계정의 패스워드
		String pw = "jr2018";

		// 데이터베이스와 연동하기 위해 DriverManager에 등록한다.
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// DriverManager 객체로부터 Connection 객체를 얻어온다.
		Connection con = DriverManager.getConnection(url, id, pw);

		log.info(con);
		
		PreparedStatement pstmt = con.prepareStatement("select now()");
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			log.info(rs.getString(1));
		
		}
		rs.close();
		pstmt.close();
		con.close();
	}
	

}
