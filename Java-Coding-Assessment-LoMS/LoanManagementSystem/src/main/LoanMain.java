package main;

import java.util.Scanner;


public class LoanMain {
	public static void main(String[] args) {
		System.out.println("+---------------------------------------------+");
    	System.out.println("|  Loan Management System - J514 - Rishab .H  |");
    	System.out.println("+---------------------------------------------+");
    	
		LoanService service = new LoanService();
		Scanner sc = new Scanner(System.in);
		int choice;

		try {
			do {
	            System.out.println("\n\n========= Main Menu =========");
				System.out.println("1. Add Customer");
				System.out.println("2. Remove Customer");
				System.out.println("3. List Loans of a Customer");
				System.out.println("4. Display All Customers");
				System.out.println("5. Apply Loan");
				System.out.println("6. Make Payment");
				System.out.println("7. Update Loan Status");
				System.out.println("0. Exit");
				System.out.print("Enter your choice: ");
				choice = sc.nextInt();
				sc.nextLine();

				switch (choice) {
					case 1:
						service.addCustomer();
						break;
					case 2:
						service.removeCustomer();
						break;
					case 3:
						service.listLoansByCustomerId();
						break;
					case 4:
						service.displayAllCustomerLoanMapping();
						break;
					case 5:
						service.applyLoan();
						break;
					case 6:
						service.makePayment();
						break;
					case 7:
						service.updateLoanStatus();
						break;
					case 0:
						System.out.println("Exiting...");
						break;
					default:
						System.out.println("Invalid choice! Try again...");
				}
			} while (choice != 0);
			
			sc.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// J514 - Rishab H
}
