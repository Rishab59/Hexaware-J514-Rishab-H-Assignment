package main;

import java.sql.*;

import dao.CourierAdminServiceImpl;
import dao.CourierUserServiceImpl;
import entity.Courier;
import entity.Employee;
import exceptions.TrackingNumberNotFoundException;
import util.DBConnUtil;


public class CoMSMain {
    public static void main(String[] args) {
        Connection conn = DBConnUtil.getConnection();
        CourierUserServiceImpl userService = new CourierUserServiceImpl(conn);
        CourierAdminServiceImpl adminService = new CourierAdminServiceImpl(conn);

        try {
        	// Create a new courier order
            Courier newCourier = new Courier(0, "Rishab", "Chennai, Tamil Nadu", "Tony", "Bengaluru, Karnataka", 2.5, "In Transit", "", new Date(System.currentTimeMillis()), 1);

            String trackingNumber = userService.placeOrder(newCourier);
            System.out.println("New Order Placed. Tracking Number: " + trackingNumber);

            // Get order status
            String status = userService.getOrderStatus(trackingNumber);
            System.out.println("Order Status for " + trackingNumber + ": " + status);

            // Add a new courier staff
            Employee newEmployee = new Employee(0, "Steve", "steve@gmail.com", "9876543210", "Delivery Executive", 18500.00);

            int newEmpId = adminService.addCourierStaff(newEmployee);
            System.out.println("New Courier Staff Added. Employee ID: " + newEmpId);
        } catch (TrackingNumberNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    // J514 - Rishab H
}
