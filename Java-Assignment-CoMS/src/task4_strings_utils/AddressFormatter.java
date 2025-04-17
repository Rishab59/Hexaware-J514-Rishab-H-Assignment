// 11.]
package task4_strings_utils;

import java.util.Scanner;


public class AddressFormatter {
	private static String capitalizeWords(String input) {
		StringBuilder result = new StringBuilder();

		String[] words = input.trim().split("\\s+");
        for (String word : words) {
            if (word.length() > 0) {
                result.append(Character.toUpperCase(word.charAt(0))) .append(word.substring(1).toLowerCase()).append(" ");
            }
        }
        
        return result.toString().trim();
    }

    private static String formatZipCode(String zip) {
        return zip.replaceAll("[^0-9]", "").substring(0, 6);
    }

    public static String formatAddress(String street, String city, String state, String zipCode) {
        street = capitalizeWords(street);
        city = capitalizeWords(city);
        state = capitalizeWords(state);
        zipCode = formatZipCode(zipCode);

        return street + ", " + city + ", " + state + " - " + zipCode;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Street: ");
        String street = sc.nextLine();

        System.out.print("Enter City: ");
        String city = sc.nextLine();

        System.out.print("Enter State: ");
        String state = sc.nextLine();

        System.out.print("Enter Zip Code (6 digits): ");
        String zip = sc.nextLine();

        String formattedAddress = formatAddress(street, city, state, zip);
        System.out.println("Formatted Address: " + formattedAddress);

        sc.close();
    }

    // J514 - Rishab H
}
