package main;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dao.ILoanRepository;
import dao.LoanRepositoryImpl;
import entity.*;
import exception.CustomerNotFoundException;
import exception.InvalidLoanException;


public class LoanService {
    private final ILoanRepository loanRepository = new LoanRepositoryImpl();
    private final Scanner sc = new Scanner(System.in);

    public LoanService() {
    }

    protected void addCustomer() {
        try {
            System.out.print("Enter customer name: ");
            String name = sc.nextLine();
            System.out.print("Enter email: ");
            String email = sc.nextLine();
            System.out.print("Enter phone: ");
            String phone = sc.nextLine();
            System.out.print("Enter address: ");
            String address = sc.nextLine();
            System.out.print("Enter credit score: ");
            int creditScore = sc.nextInt();
            sc.nextLine();

            Customer customer = new Customer(0, name, email, phone, address, creditScore);
            loanRepository.addCustomer(customer);
        } catch (Exception e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    protected void removeCustomer() {
        try {
            System.out.print("Enter customer ID: ");
            int customerId = sc.nextInt();
            sc.nextLine();

            List<Loan> loans = loanRepository.getLoansByCustomerId(customerId);
            if (!loans.isEmpty()) {
                System.out.println("Cannot remove customer. Loan(s) associated.");
                return;
            }

            loanRepository.removeCustomer(customerId);
            System.out.println("Customer removed successfully.");
        } catch (CustomerNotFoundException e) {
            System.out.println("Customer not found.");
        }
    }

    protected void applyLoan() {
        try {
            System.out.print("Enter customer ID: ");
            int customerId = sc.nextInt();
            sc.nextLine();

            Customer customer = loanRepository.getCustomerById(customerId);

            System.out.print("Enter principal amount: ");
            double principal = sc.nextDouble();
            System.out.print("Enter interest rate: ");
            double rate = sc.nextDouble();
            System.out.print("Enter loan term (in months): ");
            int term = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter loan type (Home/Car): ");
            String type = sc.nextLine();

            Loan loan = null;

            if (type.equalsIgnoreCase("Home")) {
                System.out.print("Enter property address: ");
                String propAddr = sc.nextLine();
                System.out.print("Enter property value: ");
                double propVal = sc.nextDouble();
                sc.nextLine();

                loan = new HomeLoan(0, customer, principal, rate, term, "Pending", propAddr, propVal);
            } else if (type.equalsIgnoreCase("Car")) {
                System.out.print("Enter car model: ");
                String carModel = sc.nextLine();
                System.out.print("Enter car value: ");
                double carVal = sc.nextDouble();
                sc.nextLine();

                loan = new CarLoan(0, customer, principal, rate, term, "Pending", carModel, carVal);
            } else {
                System.out.println("Invalid loan type.");
                return;
            }

            loanRepository.applyLoan(loan);
        } catch (CustomerNotFoundException e) {
            System.out.println("Customer not found.");
        } catch (Exception e) {
            System.out.println("Error applying loan: " + e.getMessage());
        }
    }

    protected void makePayment() {
        try {
            System.out.print("Enter Loan ID: ");
            int loanId = sc.nextInt();
            sc.nextLine();

            String status = loanRepository.getLoanStatus(loanId);
            if (!"Approved".equalsIgnoreCase(status)) {
                System.out.println("Payment not allowed. Loan status is not 'Approved'.");
                return;
            }

            double emi = loanRepository.calculateEMI(loanId);
            double roundedEMI = Math.round(emi * 100.0) / 100.0;
            System.out.println("EMI Amount: Rs." + String.format("%.2f", roundedEMI));

            double amount;
            System.out.print("Enter payment amount: ");
            amount = sc.nextDouble();
            sc.nextLine();

            loanRepository.loanRepayment(loanId, amount);
        } catch (InvalidLoanException e) {
            System.out.println("Invalid loan.");
        } catch (SQLException e) {
            System.out.println("SQL Error during payment.");
        }
    }

    protected void updateLoanStatus() throws CustomerNotFoundException, SQLException {
        try {
            System.out.print("Enter loan ID: ");
            int loanId = sc.nextInt();
            sc.nextLine();

            Loan loan = loanRepository.getLoanById(loanId);
            Customer customer = loan.getCustomer();
            
            if (customer == null) {
            	throw new CustomerNotFoundException("Customer not found.");
            }
            
            int creditScore = customer.getCreditScore();
            System.out.println("Current Credit score of the Customer (ID: " + customer.getCustomerId() + "): " + creditScore);
            
            if (creditScore < 650) {
            	loan.setLoanStatus("Rejected");
            	loanRepository.updateLoanStatus(loan.getLoanId(), "Rejected");
            	System.out.println("Loan Rejected. Reason: Credit Score < 650.");
            } 
            
            System.out.println("What do you want the status to be updated (Approved / Rejected): ");
            String status = sc.nextLine();
            
            if (status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("a")) {
                loan.setLoanStatus("Approved");
                loanRepository.updateLoanStatus(loan.getLoanId(), "Approved");
                System.out.println("Loan Approved.");
                
                return;
            } else {
            	System.out.println("Are you sure you want to Reject the loan even if credit score > 650 (yes / no): ");
            	String choice = sc.nextLine();
            	
            	if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y")) {
            		loan.setLoanStatus("Rejected");
            		loanRepository.updateLoanStatus(loan.getLoanId(), "Rejected");
            		System.out.println("Loan Rejected. Reason: Other criterias not met.");            		
            	} else {
            		System.out.println("Operation Aborted. Returning to Main Menu");
            		return;
            	}
            }
        } catch (InvalidLoanException e) {
            System.out.println("Invalid loan.");
        }
    }

    protected void listLoansByCustomerId() {
        try {
            System.out.print("Enter customer ID: ");
            int customerId = sc.nextInt();
            sc.nextLine();

            List<Loan> loans = loanRepository.getLoansByCustomerId(customerId);
            if (loans.isEmpty()) {
                System.out.println("No loans found for the customer.");
            } else {
                for (Loan l : loans) {
                    System.out.println(l);
                }
            }
        } catch (CustomerNotFoundException e) {
            System.out.println("Customer not found.");
        }
    }

    protected void displayAllCustomerLoanMapping() {
        loanRepository.displayAllCustomerLoanMapping();
    }
    
    // J514 - Rishab H
}
