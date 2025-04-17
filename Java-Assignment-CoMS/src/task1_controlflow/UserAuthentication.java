// 3.]
package task1_controlflow;

import java.util.Scanner;


public class UserAuthentication {
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);

    	String empEmail = "john@example.com";
        String empPass = "emp123";
        String custEmail = "alice@example.com";
        String custPass = "pass123";

        System.out.print("Enter role (employee/customer): ");
        String role = sc.nextLine().toLowerCase();

        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (role.equals("employee")) {
            if (email.equals(empEmail) && password.equals(empPass)) {
                System.out.println("Employee login successful.");
            } else {
                System.out.println("Invalid employee credentials.");
            }
        } else if (role.equals("customer")) {
            if (email.equals(custEmail) && password.equals(custPass)) {
                System.out.println("Customer login successful.");
            } else {
                System.out.println("Invalid customer credentials.");
            }
        } else {
            System.out.println("Invalid role selected.");
        }

        sc.close();
    }
    
    // J514 - Rishab H
}
