package entity;

import java.util.ArrayList;
import java.util.List;


public class CourierCompanyCollection {
    private List<Courier> couriers;

    public CourierCompanyCollection() {
        this.couriers = new ArrayList<>();
    }

    public List<Courier> getCouriers() {
        return couriers;
    }

    public void addCourier(Courier courier) {
        couriers.add(courier);
    }

    public void removeCourier(Courier courier) {
        couriers.remove(courier);
    }

    public List<Courier> getAssignedOrders(int courierStaffId) {
        List<Courier> assignedOrders = new ArrayList<>();
        
        for (Courier courier : couriers) {
            if (courier.getAssignedStaffId() == courierStaffId) {
                assignedOrders.add(courier);
            }
        }
        
        return assignedOrders;
    }
    
    // J514 - Rishab H
}
