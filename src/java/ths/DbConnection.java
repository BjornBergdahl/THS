/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ths;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Björn Bergdahl
 * @author Andreas Kuoppa
 */
public class DbConnection {
    public static String HOST = "jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false";
    public static String USERNAME = "root";
     public static String PASSWORD = "Remington870E";
//    public static String PASSWORD = "meiofasknasmisse123";
       
    public static ResultSet runSp(String sp) throws SQLException{
        Connection con = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        String sql = "{call " + sp + "}";
        CallableStatement stmt = con.prepareCall(sql);
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    } 
    
}
