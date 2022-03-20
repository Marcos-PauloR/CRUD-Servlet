package estudos.pessoas.dao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;


public class BaseDao {
	
	public BaseDao() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
		}		
		
	}
	
	public Connection getConnection() throws SQLException{
		String url = "jdbc:mysql://localhost/pessoas";
		Connection conn = DriverManager.getConnection(url, "root", "marcos");
		return conn;
	} 
	
	public static void main(String[] args) throws SQLException {
		BaseDao bd = new BaseDao();
		Connection conn = bd.getConnection();
		if(conn != null) {
			System.out.println("Funfou! ");
		}else {
			System.out.println("Não Funfou!");
		}
	}
	
	
	
	
}
