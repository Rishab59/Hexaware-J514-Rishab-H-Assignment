package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entity.Courier;
import exceptions.TrackingNumberNotFoundException;
import service.ICourierUserService;


public class CourierUserServiceImpl implements ICourierUserService {
    private Connection conn;

    public CourierUserServiceImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public String placeOrder(Courier courierObj) {
        String trackingNumber = "TRACK" + (Math.random() * 10000);
        courierObj.setTrackingNumber(trackingNumber);

        try {
            String sql = "INSERT INTO courier (trackingnumber, status, assigned_staff_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, trackingNumber);
            stmt.setString(2, courierObj.getStatus());
            stmt.setInt(3, courierObj.getAssignedStaffId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trackingNumber;
    }

    @Override
    public String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException {
        String status = null;
        
        try {
            String sql = "SELECT status FROM courier WHERE trackingnumber = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, trackingNumber);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                status = rs.getString("status");
            } else {
                return "Tracking number not found.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return status;
    }

    @Override
    public boolean cancelOrder(String trackingNumber) throws TrackingNumberNotFoundException {
        try {
            String sql = "DELETE FROM courier WHERE trackingnumber = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, trackingNumber);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return true;
    }

    @Override
    public List<Courier> getAssignedOrder(int courierStaffId) {
        List<Courier> assignedOrders = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM courier WHERE assigned_staff_id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, courierStaffId);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Courier courier = new Courier();
                courier.setTrackingNumber(rs.getString("trackingnumber"));
                courier.setStatus(rs.getString("status"));
                courier.setAssignedStaffId(rs.getInt("assigned_staff_id"));
                assignedOrders.add(courier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return assignedOrders;
    }
    
    // J514 - Rishab H
}
