// 12.]
package task4_strings_utils;

import java.util.Scanner;


public class OrderConfirmationEmail {

    public static String generateEmail(String customerName, String orderNumber, String deliveryAddress, String deliveryDate) {
        StringBuilder email = new StringBuilder();
        
        email.append("Hello ").append(customerName).append(",\n\n");
        email.append("Thank you for placing your order with Courier Management System.\n");
        email.append("Your order number is ").append(orderNumber).append(".\n");
        email.append("It will be delivered to the following address:\n");
        email.append(deliveryAddress).append("\n\n");
        email.append("Expected Delivery Date: ").append(deliveryDate).append("\n\n");
        email.append("Thank you for choosing our service.\n");
        email.append("Regards,\n");
        email.append("CoMS Team");
        
        return email.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter customer name: ");
        String customerName = sc.nextLine();

        System.out.print("Enter order number: ");
        String orderNumber = sc.nextLine();

        System.out.print("Enter delivery address: ");
        String deliveryAddress = sc.nextLine();

        System.out.print("Enter expected delivery date (YYYY-MM-DD): ");
        String deliveryDate = sc.nextLine();

        String emailContent = generateEmail(customerName, orderNumber, deliveryAddress, deliveryDate);
        
        System.out.println("\n===== ORDER CONFIRMATION EMAIL =====");
        System.out.println(emailContent);

        sc.close();
    }

    // J514 - Rishab H
}
