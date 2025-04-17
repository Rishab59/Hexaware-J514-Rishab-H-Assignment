package service;

import java.util.List;

import entity.Courier;
import exceptions.TrackingNumberNotFoundException;


public interface ICourierUserService {
    String placeOrder(Courier courierObj);
    
    String getOrderStatus(String trackingNumber) throws TrackingNumberNotFoundException;
    
    boolean cancelOrder(String trackingNumber) throws TrackingNumberNotFoundException;
    
    List<Courier> getAssignedOrder(int courierStaffId);
    
    // J514 - Rishab H
}
