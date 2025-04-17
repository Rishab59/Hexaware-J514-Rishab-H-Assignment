// 4.]
package task1_controlflow;


public class CourierAssignmentLogic {
    public static void main(String[] args) {
        int[] courierIds = {101, 102, 103};
        double[] distances = {10.5, 5.2, 7.8};
        double[] capacities = {10.0, 5.0, 15.0};

        double parcelWeight = 6.0;

        int assignedCourierId = -1;
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < courierIds.length; i++) {
            if (capacities[i] >= parcelWeight && distances[i] < minDistance) {
                minDistance = distances[i];
                assignedCourierId = courierIds[i];
            }
        }

        if (assignedCourierId != -1) {
            System.out.println("Assigned Courier ID: " + assignedCourierId);
        } else {
            System.out.println("No courier available for this parcel weight.");
        }        
    }
    
    // J514 - Rishab H
}
