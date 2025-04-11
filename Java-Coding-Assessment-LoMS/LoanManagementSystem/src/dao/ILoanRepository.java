package dao;

import entity.Customer;
import entity.Loan;
import exception.InvalidLoanException;
import exception.CustomerNotFoundException;

import java.sql.SQLException;
import java.util.List;


public interface ILoanRepository {
    void applyLoan(Loan loan) throws CustomerNotFoundException;
    double calculateInterest(int loanId) throws InvalidLoanException;
    double calculateEMI(int loanId) throws InvalidLoanException;
    void updateLoanStatus(int loanId, String status) throws InvalidLoanException, SQLException;
    void loanRepayment(int loanId, double amount) throws InvalidLoanException, SQLException;
    
    void loanStatus(int loanId) throws InvalidLoanException;
    List<Loan> getAllLoans();
    Loan getLoanById(int loanId) throws InvalidLoanException;
    String getLoanStatus(int loanId) throws InvalidLoanException, SQLException;

    void addCustomer(Customer customer);
    void removeCustomer(int customerId) throws CustomerNotFoundException;
    Customer getCustomerById(int customerId) throws CustomerNotFoundException;
    List<Loan> getLoansByCustomerId(int customerId) throws CustomerNotFoundException;

    void displayAllCustomerLoanMapping();
    
    // J514 - Rishab H
}