package dao;

import java.sql.*;

import entity.Employee;
import service.ICourierAdminService;


public class CourierAdminServiceImpl extends CourierUserServiceImpl implements ICourierAdminService {

    private static Connection conn;
    
    public CourierAdminServiceImpl(Connection conn) {
        super(conn);
    }

    @Override
    public int addCourierStaff(Employee obj) {
        try {
            String sql = "INSERT INTO employee (name, contactnumber, role, salary) VALUES (?, ?, ?, ?)";
            
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            stmt.setString(1, obj.getEmployeeName());
            stmt.setString(2, obj.getContactNumber());
            stmt.setString(3, obj.getRole());
            stmt.setDouble(4, obj.getSalary());
            
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    // J514 - Rishab H
}
