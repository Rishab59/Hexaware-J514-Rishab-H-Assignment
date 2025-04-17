package dao;

import java.sql.Connection;

import entity.Employee;
import exceptions.InvalidEmployeeIdException;
import service.ICourierAdminService;


public class CourierAdminServiceCollectionImpl extends CourierUserServiceCollectionImpl implements ICourierAdminService {
	public CourierAdminServiceCollectionImpl(Connection conn) {
		super(conn);
	}

    @Override
    public int addCourierStaff(Employee obj) throws InvalidEmployeeIdException {
        if (obj == null || obj.getEmployeeName() == null || obj.getEmployeeName().isEmpty()) {
            throw new InvalidEmployeeIdException("Invalid employee details provided.");
        }
        
        return obj.getEmployeeID();
    }
    
    // J514 - Rishab H
}
