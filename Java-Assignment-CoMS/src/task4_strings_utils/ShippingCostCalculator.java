// 13.]
package task4_strings_utils;


public class ShippingCostCalculator {
    public static double calculateDistance(String source, String destination) {
        int srcValue = source.replaceAll("\\s+", "").length();
        int destValue = destination.replaceAll("\\s+", "").length();
        int diff = Math.abs(srcValue - destValue);

        return diff * 10.0;
    }

    public static double calculateShippingCost(String source, String destination, double weightKg) {
        double distance = calculateDistance(source, destination);
        double baseRate = 5.0;
        
        return ((distance / 100.0) * weightKg * baseRate);
    }

    public static void main(String[] args) {
        String source = "Chennai";
        String destination = "Bangalore";
        double weight = 4.5;

        double cost = calculateShippingCost(source, destination, weight);
        
        System.out.printf("Shipping cost from %s to %s for %.2f kg is ₹%.2f\n", source, destination, weight, cost);
    }

    // J514 - Rishab H
}
