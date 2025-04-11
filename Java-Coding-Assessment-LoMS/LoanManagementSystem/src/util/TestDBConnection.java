package util;

import java.sql.Connection;


public class TestDBConnection {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnUtil.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection successful!");
                conn.close();
            } else {
                System.out.println("Connection failed: No connection returned.");
            }
        } catch (Exception e) {
            System.out.println("Error while connecting to database: " + e.getMessage());
        }
    }
    
    // J514 - Rishab H
}
