// 2.]
package task1_controlflow;

import java.util.Scanner;


public class ParcelWeightCategorizer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter parcel weight in kg: ");
        double weight = sc.nextDouble();

        String category = (weight < 2.0) ? "Light" : (weight <= 5.0) ? "Medium" : "Heavy";

        switch (category) {
            case "Light":
                System.out.println("This is a Light parcel.");
                break;
            case "Medium":
                System.out.println("This is a Medium parcel.");
                break;
            case "Heavy":
                System.out.println("This is a Heavy parcel.");
                break;
            default:
                System.out.println("Invalid weight category.");
        }
        sc.close();
    }
    
    // J514 - Rishab H
}
