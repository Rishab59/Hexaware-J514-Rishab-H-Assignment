// 14.]
package task4_strings_utils;

import java.util.Random;


public class PasswordGenerator {
    public static String generatePassword(int length) {
    	StringBuilder password = new StringBuilder();
    	Random random = new Random();

    	String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialCharacters = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/";

        String allCharacters = upperCaseLetters + lowerCaseLetters + digits + specialCharacters;

        password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));
        password.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

        for (int i = 4; i < length; i++) {
            password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }
        
        for (int i = 0; i < password.length(); i++) {
            int j = random.nextInt(password.length());
            char temp = password.charAt(i);
            
            password.setCharAt(i, password.charAt(j));
            password.setCharAt(j, temp);
        }

        return password.toString();
    }

    public static void main(String[] args) {
        int passwordLength = 12;
        String generatedPassword = generatePassword(passwordLength);
        
        System.out.println("Generated password: " + generatedPassword);
    }

    // J514 - Rishab H
}
