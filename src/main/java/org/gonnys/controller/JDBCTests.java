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

		// ����Ϸ��� �����ͺ��̽����� ������ URL ���
		String url = "jdbc:mysql://localhost:3306/jr?useSSL=false&serverTimezone=UTC";
		// ����� ����
		String id = "jr2018";
		// ����� ������ �н�����
		String pw = "jr2018";

		// �����ͺ��̽��� �����ϱ� ���� DriverManager�� ����Ѵ�.
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		// DriverManager ��ü�κ��� Connection ��ü�� ���´�.
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
