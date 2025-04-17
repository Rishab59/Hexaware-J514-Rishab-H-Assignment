// 8.]
package task3_arrays;


class Courier {
    String name;
    double distanceFromPickup;

    Courier(String name, double distanceFromPickup) {
        this.name = name;
        this.distanceFromPickup = distanceFromPickup;
    }
}

public class NearestCourierFinder {
	public static Courier findNearestCourier(Courier[] couriers) {
        Courier nearest = couriers[0];

        for (Courier c : couriers) {
            if (c.distanceFromPickup < nearest.distanceFromPickup) {
                nearest = c;
            }
        }

        return nearest;
    }
	
    public static void main(String[] args) {
        Courier[] couriers = {
            new Courier("Raj", 12.5),
            new Courier("Amit", 5.2),
            new Courier("Kiran", 7.0),
            new Courier("Sneha", 4.8)
        };

        Courier nearest = findNearestCourier(couriers);
        System.out.println("Nearest available courier: " + nearest.name + " (" + nearest.distanceFromPickup + " km away)");
    }

    // J514 - Rishab H
}
