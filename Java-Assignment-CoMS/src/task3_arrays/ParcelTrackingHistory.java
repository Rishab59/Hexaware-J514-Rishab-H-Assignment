// 7.]
package task3_arrays;


public class ParcelTrackingHistory {
    public static void main(String[] args) {
        String[] trackingHistory = {
            "Dispatched from Warehouse A",
            "Arrived at Sorting Center",
            "Out for Delivery",
            "Delivered to Receiver"
        };

        System.out.println("Tracking History:");
        for (String location : trackingHistory) {
            System.out.println("- " + location);
        }
    }
    
    // J514 - Rishab H
}
