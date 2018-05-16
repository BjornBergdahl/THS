package TicketModel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 *
 * Tests database SQL and stored procedures
 * 
 * @author Björn Bergdahl
 * @author Andreas Kuoppa
 */
public class TestDb {
    
    public static String HOST = "jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false";
    public static String USERNAME = "root";
    public static String PASSWORD = "Remington870E";
    // public static String PASSWORD = "meiofasknasmisse123";
    
    public static void runSql(String sql){
        try (Connection con = DriverManager.getConnection(HOST, USERNAME, PASSWORD)) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            printResultset(rs);
            con.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage( ));
        }   
    }
    
    public static void runSp(String sp){
        try (Connection con = DriverManager.getConnection(HOST, USERNAME, PASSWORD)) {
            String sql = "{call " + sp + "}";
            CallableStatement stmt = con.prepareCall(sql);
            ResultSet rs = stmt.executeQuery(sql);
            printResultset(rs);
            con.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }           
    }
    
    private static void printResultset(ResultSet rs){
        int SPACING = 4;
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            System.out.println("\n----- ResultSet -----\n");
            // prints column names
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                int x = i;
                if (x == 1) x++;
                System.out.printf("%-" + Integer.toString(rsmd.getPrecision(x) + SPACING)+ "s", rsmd.getColumnName(i));
            }
            System.out.println("\n");
            // prints table data
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = rs.getString(i);
                    int x = i;
                    if (x == 1) x ++;
                    System.out.printf("%-" + Integer.toString(rsmd.getPrecision(x) + SPACING)+ "s", columnValue);
                }
                System.out.println();
            }
            System.out.println("\n----- /ResultSet -----\n");
        }    
        catch (SQLException e) {
            System.out.println("printResultSet failed: " + e.getMessage());
        }
    }
}

 
   

