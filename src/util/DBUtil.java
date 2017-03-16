package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtil {
	private static String driver="com.mysql.jdbc.Driver";
	private static String url="jdbc:mysql://192.168.1.19:3306/db_ibaixiong?useUnicode=true&amp;characterEncoding=utf8";
	private static String username="ibaixiong";
	private static String password="1234567890";
	public static Connection getConnect(){
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void close(Connection conn,PreparedStatement ps){
		try {
			if(conn!=null){
				conn.close();
			}
			if(ps!=null){
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		System.out.println(DBUtil.getConnect());
	}
}
