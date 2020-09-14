package goru_firstDB;

import java.sql.*;

public class goru {
	public static void main(String[] args) {
		String result = "", strQ = "Select * From sample";
		DB_MAN DBM = new DB_MAN(); //DB를 열고 실행시키고 닫아주는 class 객체 생성
	
		try {
			DBM.dbOpen();
			DBM.DB_rs = DBM.DB_stmt.executeQuery(strQ);
			while(DBM.DB_rs.next()) {
				result += DBM.DB_rs.getString("SUBJECT")+"\n";
			} DBM.dbClose();
		} catch(Exception e) {
		
		} finally {
			try {
				if(DBM.DB_stmt != null) DBM.DB_stmt.close();
			} catch(SQLException ex1) {
			
			}try {
				if(DBM.DB_con != null) DBM.DB_con.close();
			} catch(SQLException ex1) {
			
			}
		}
		System.out.print(result);
	}
	
}