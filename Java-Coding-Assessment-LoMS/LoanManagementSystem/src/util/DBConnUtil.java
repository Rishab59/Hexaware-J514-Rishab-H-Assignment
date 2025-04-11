package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnUtil {
	private static final String URL = "jdbc:mysql://localhost:3306/LoanManagementDB";
    private static final String USER = "root";
    private static final String PASSWORD = "rishab..";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database!\n", e);
        }
    }
    
    // J514 - Rishab H
}
