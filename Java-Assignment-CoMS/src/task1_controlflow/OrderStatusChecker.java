// 1.]
package task1_controlflow;

import java.util.Scanner;


public class OrderStatusChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter order status: ");
        String status = sc.nextLine();

        if (status.equalsIgnoreCase("Delivered")) {
            System.out.println("The order has been delivered.");
        } else if (status.equalsIgnoreCase("Processing")) {
            System.out.println("The order is still being processed.");
        } else if (status.equalsIgnoreCase("Cancelled")) {
            System.out.println("The order was cancelled.");
        } else {
            System.out.println("Unknown status entered.");
        }
        
        sc.close();  
    }
    
    // J514 - Rishab H
}
