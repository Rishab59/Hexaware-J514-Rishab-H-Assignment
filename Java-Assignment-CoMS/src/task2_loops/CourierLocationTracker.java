// 6.]
package task2_loops;


public class CourierLocationTracker {
    public static void main(String[] args) throws InterruptedException {
        String[] path = {
            "Warehouse A",
            "City Center",
            "Main Street",
            "Sector 59",
            "Receiver's Address"
        };

        int currentIndex = 0;
        System.out.println("Tracking Courier Location...");

        while (currentIndex < path.length) {
            System.out.println("Current Location: " + path[currentIndex]);
            
            currentIndex++;
        }

        System.out.println("Courier has reached the destination.");
    }

    // J514 - Rishab H
}
