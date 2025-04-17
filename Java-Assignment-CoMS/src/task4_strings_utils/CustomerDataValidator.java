// 10.]
package task4_strings_utils;

import java.util.Scanner;


public class CustomerDataValidator {
    public static boolean validate(String data, String detail) {
        switch (detail.toLowerCase()) {
            case "name":
                return data.matches("[A-Z][a-z]*( [A-Z][a-z]*)*");
            case "address":
                return data.matches("[a-zA-Z0-9\\s,.-]+");
            case "phone":
                return data.matches("\\d{3}-\\d{3}-\\d{4}");
            default:
                return false;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter data to validate: ");
        String data = sc.nextLine();

        System.out.print("Enter detail type (name/address/phone): ");
        String detail = sc.nextLine();

        boolean isValid = validate(data, detail);
        if (isValid) {
            System.out.println(detail + " is valid.");
        } else {
            System.out.println(detail + " is invalid.");
        }

        sc.close();
    }

    // J514 - Rishab H
}
