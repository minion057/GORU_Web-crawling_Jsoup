package goru_firstDB;

import java.io.IOException;
import java.sql.*;

public class DB_MAN {
    //MySQL을 접속할 정보
    String strDriver = "com.mysql.jdbc.Driver"; 
    String strURL = "jdbc:mysql://localhost/test0408";
    String strUser = "root";
    String strPWD = "ohgg805**";
    
    Connection DB_con; //DB Connection
    Statement DB_stmt; //To store statement for DB Connection
    ResultSet DB_rs; //To store result of SQL Execution
    
    //DB를 열고 SQL문을 실행하는 메소드
    public void dbOpen() throws IOException{
        try {
            Class.forName(strDriver); //Load JDBC-ODBC bridge driver
            DB_con = DriverManager.getConnection(strURL, strUser, strPWD); //Setting DSN Prooerties in driver
            DB_stmt = DB_con.createStatement(); //To create statement for DB Connection
        } catch (Exception e) {
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    
    //DB를 닫는 메소드
    public void dbClose() throws IOException{
        try {
            DB_stmt.close(); //Exit Statement Connection
            DB_con.close(); //exit DB Connection
        } catch (Exception e) {
            System.out.println("SQLException : "+e.getMessage());
        }
    }

}
