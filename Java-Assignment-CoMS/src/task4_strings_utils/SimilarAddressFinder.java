// 15.]
package task4_strings_utils;

import java.util.ArrayList;
import java.util.List;


public class SimilarAddressFinder {
    public static List<String> findSimilarAddresses(List<String> addressList, String address) {
        List<String> similarAddresses = new ArrayList<>();
        
        for (String storedAddress : addressList) {
            if (storedAddress.toLowerCase().contains(address.toLowerCase())) {
                similarAddresses.add(storedAddress);
            }
        }
        
        return similarAddresses;
    }

    public static void main(String[] args) {
        List<String> addressList = new ArrayList<>();
        
        addressList.add("123 Main Street, Cityville");
        addressList.add("456 Park Avenue, Townsville");
        addressList.add("789 Broad Road, Cityville");
        addressList.add("321 Central Avenue, Cityville");

        String addressToFind = "Cityville";

        List<String> similarAddresses = findSimilarAddresses(addressList, addressToFind);

        System.out.println("Similar addresses found:");
        for (String similarAddress : similarAddresses) {
            System.out.println(similarAddress);
        }
    }

    // J514 - Rishab H
}
