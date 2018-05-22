package controllerOP;
import java.sql.*;

public class authMysql {
	private static Connection conn;
	private static ResultSet rs;
	private static PreparedStatement psql;
	private static Connection connect(){
		conn=null;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/test";
		String user = "root";
		String password = "root";
		 try { 
	            // ������������
	            Class.forName(driver);
	            // �������ݿ�
	            conn = DriverManager.getConnection(url, user, password);
	            }catch(Exception e){System.out.println("connect to mysql faild!");}
		return conn;
	}
	public static ResultSet searchSQL(String sql){
		rs=null;
		conn=connect();
		try {
			if(!conn.isClosed()){
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(sql);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	public static void operateSQL(String sql){
		conn=connect();
		psql=null;
		try {
			psql = conn.prepareStatement(sql);
			psql.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void close(){
		try{
			if(rs!=null)
				rs.close();			
		}catch(SQLException e){
			System.out.println("�ر�rs����ʧ�ܣ�");
			e.printStackTrace();
		}
		try{
			if(psql!=null)
				psql.close();			
		}catch(SQLException e){
			System.out.println("�ر�psql����ʧ�ܣ�");
			e.printStackTrace();
		}
		try{
			if(conn!=null){
				conn.close();
			}
		}catch(SQLException e){
			System.out.println("�ر�con����ʧ�ܣ�");
			e.printStackTrace();
		}
	}
}
