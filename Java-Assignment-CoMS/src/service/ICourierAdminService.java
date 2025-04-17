package service;

import entity.Employee;
import exceptions.InvalidEmployeeIdException;


public interface ICourierAdminService {
    int addCourierStaff(Employee obj) throws InvalidEmployeeIdException;
    
    // J514 - Rishab H
}
