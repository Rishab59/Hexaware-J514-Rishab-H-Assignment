// 9.]
package task4_strings_utils;

import java.util.Scanner;


public class ParcelTracking {

    static String[][] parcelStatus = {
        {"TRACK123", "in transit"},
        {"TRACK456", "out for delivery"},
        {"TRACK789", "delivered"}
    };

    public static void trackParcel(String trackingNumber) {
        boolean found = false;
        for (String[] parcel : parcelStatus) {
            if (parcel[0].equalsIgnoreCase(trackingNumber)) {
                System.out.println("Tracking Number: " + parcel[0]);
                System.out.println("Current Status: " + parcel[1]);
                
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Tracking number not found.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter tracking number: ");
        String trackingNumber = sc.nextLine();
        
        trackParcel(trackingNumber);
        
        sc.close();
    }

    // J514 - Rishab H
}
