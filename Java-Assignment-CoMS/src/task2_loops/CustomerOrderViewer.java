// 5.] 
package task2_loops;

import java.sql.*;
import java.util.*;


public class CustomerOrderViewer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter sender name to view orders: ");
        String senderName = sc.nextLine();

        String url = "jdbc:mysql://localhost:3306/CoMS";
        String user = "root";
        String password = "rishab..";

        try {
        	Connection conn = DriverManager.getConnection(url, user, password);
        	
            String query = "SELECT * FROM courier WHERE sendername = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            pstmt.setString(1, senderName);

            ResultSet rs = pstmt.executeQuery();

            rs.last();
            int total = rs.getRow();
            rs.beforeFirst();

            if (total == 0) {
                System.out.println("No orders found for sender: " + senderName);
            } else {
                System.out.println("Total Orders: " + total);
                for (int i = 1; i <= total; i++) {
                    rs.absolute(i);
                    
                    System.out.println("Order " + i + ": Tracking #" + rs.getString("trackingnumber") + ", Status: " + rs.getString("status") + ", Delivery Date: " + rs.getDate("deliverydate"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        sc.close();
    }
    
    // J514 - Rishab H
}
