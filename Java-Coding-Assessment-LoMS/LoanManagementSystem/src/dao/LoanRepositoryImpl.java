package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entity.*;
import exception.CustomerNotFoundException;
import exception.InvalidLoanException;
import util.DBConnUtil;


public class LoanRepositoryImpl implements ILoanRepository {
	private Connection conn;
	private Scanner sc;

	public LoanRepositoryImpl() {
		this.conn = DBConnUtil.getConnection();
		sc = new Scanner(System.in);
	}

	@Override
	public void applyLoan(Loan loan) throws CustomerNotFoundException {
		System.out.print("Are you sure you want to apply for the loan? (Yes/No): ");
		String confirm = sc.nextLine();
		if (!confirm.equalsIgnoreCase("Yes")) {
			System.out.println("Loan application cancelled.");
			return;
		}

		try {
			PreparedStatement checkCustomer = conn.prepareStatement("SELECT * FROM customers WHERE customer_id = ?");
			checkCustomer.setInt(1, loan.getCustomer().getCustomerId());

			ResultSet rs = checkCustomer.executeQuery();
			if (!rs.next()) {
				throw new CustomerNotFoundException("Customer not found.");
			}

			PreparedStatement ps = conn.prepareStatement("INSERT INTO loans (customer_id, principal_amount, interest_rate, loan_term, loan_type, loan_status) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, loan.getCustomer().getCustomerId());
			ps.setDouble(2, loan.getPrincipalAmount());
			ps.setDouble(3, loan.getInterestRate());
			ps.setInt(4, loan.getLoanTerm());
			ps.setString(5, loan.getLoanType());
			ps.setString(6, "Pending");

			ps.executeUpdate();

			ResultSet keys = ps.getGeneratedKeys();
			if (keys.next()) {
				int loanId = keys.getInt(1);
				System.out.println("Loan applied successfully with Loan ID: " + loanId);
			}

			// Calculate and display interest and EMI
			double interest = calculateInterest(loan.getPrincipalAmount(), loan.getInterestRate(), loan.getLoanTerm());
			double emi = calculateEMI(loan.getPrincipalAmount(), loan.getInterestRate(), loan.getLoanTerm());
			
			System.out.println("Interest Amount: Rs." + interest);
			System.out.println("EMI Amount: Rs." + emi);
			System.out.println("Loan Status: Pending");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private double calculateInterest(double principal, double rate, int tenure) {
		return (principal * rate * tenure) / 12 / 100;
	}

	@Override
	public double calculateInterest(int loanId) throws InvalidLoanException {
		String query = "SELECT principal_amount, interest_rate, loan_term FROM loans WHERE loan_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, loanId);
			
			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				throw new InvalidLoanException("Loan not found.");
			}

			double principal = rs.getDouble("principal_amount");
			double rate = rs.getDouble("interest_rate");
			int tenure = rs.getInt("loan_term");

			double interest = calculateInterest(principal, rate, tenure);
			System.out.println("Interest Amount: Rs." + String.format("%.2f", interest));
			
			return interest;
		} catch (SQLException e) {
			throw new InvalidLoanException("Error calculating interest.");
		}
	}
	
	private double calculateEMI(double principal, double annualRate, int tenure) {
		double monthlyRate = annualRate / 12 / 100;
		
		return (principal * monthlyRate * Math.pow(1 + monthlyRate, tenure)) / (Math.pow(1 + monthlyRate, tenure) - 1);
	}
	
	@Override
	public double calculateEMI(int loanId) throws InvalidLoanException {
		String query = "SELECT principal_amount, interest_rate, loan_term FROM loans WHERE loan_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, loanId);
			
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				throw new InvalidLoanException("Loan not found.");
			}

			double principal = rs.getDouble("principal_amount");
			double annualRate = rs.getDouble("interest_rate");
			int tenure = rs.getInt("loan_term");

			double emi = calculateEMI(principal, annualRate, tenure);
			
			return emi;
		} catch (SQLException e) {
			throw new InvalidLoanException("Error calculating EMI.");
		}
	}

	@Override
	public void updateLoanStatus(int loanId, String status) throws InvalidLoanException, SQLException {
		String query = "UPDATE loans SET loan_status = ? WHERE loan_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, status);
			ps.setInt(2, loanId);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new InvalidLoanException("Error calculating interest.");
		}
	}
	
	@Override
	public void loanRepayment(int loanId, double amount) throws InvalidLoanException, SQLException {
		String status = getLoanStatus(loanId);
		if (!"Approved".equalsIgnoreCase(status)) {
			System.out.println("Payments can only be made on approved loans.");
			return;
		}

		double emi = calculateEMI(loanId);
		double roundedEMI = Math.round(emi * 100.0) / 100.0; // Round off to 2 decimals

		if (Math.round(amount * 100.0) / 100.0 < roundedEMI) {
			System.out.println("EMI Amount: Rs." + String.format("%.2f", roundedEMI));
			System.out.println("\nPayment Status: Failed\nReason: Amount is less than one EMI.");
			
			return;
		}

		if (amount > roundedEMI) {
			System.out.println("EMI Amount: Rs." + String.format("%.2f", roundedEMI));
			System.out.print("Amount is more than one EMI. Do you want to proceed? (Yes/No): ");
			String response = sc.nextLine();
			
			if (!response.equalsIgnoreCase("yes")) {
				System.out.println("\nPayment Status: Failed\nReason: Payment Cancelled.");
				return;
			}
		}

		// Retrieve loan details
		Loan loan = getLoanById(loanId);
		double principal = loan.getPrincipalAmount();
		double interest = calculateInterest(loanId);

		System.out.println("Interest Amount: Rs." + String.format("%.2f", interest));
		double totalBalance = principal + interest;
		System.out.println("Remaining Loan Balance (Principal + Interest): ₹" + String.format("%.2f", totalBalance));

		double principalToDeduct = amount - interest;
		double updatedPrincipal = principal - principalToDeduct;

		if (updatedPrincipal < 0) {
			updatedPrincipal = 0;
		}
		
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE loans SET principal_amount = ? WHERE loan_id = ?");
			ps.setDouble(1, updatedPrincipal);
			ps.setInt(2, loanId);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new InvalidLoanException("Error Updating Principle Amount.");
		}

		System.out.println("\nPayment Status: Successful.");
		System.out.println("Updated Principal: Rs." + String.format("%.2f", updatedPrincipal));
		System.out.println("New Balance (Principal + Interest): Rs."+ String.format("%.2f", updatedPrincipal + calculateInterest(loanId)));
	}

	@Override
	public void loanStatus(int loanId) throws InvalidLoanException {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT l.loan_id, c.credit_score FROM loans l JOIN customers c ON l.customer_id = c.customer_id WHERE l.loan_id = ?");
			ps.setInt(1, loanId);
			
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				throw new InvalidLoanException("Loan not found.");
			}

			int creditScore = rs.getInt("credit_score");
			String status = creditScore >= 650 ? "Approved" : "Rejected";

			PreparedStatement update = conn.prepareStatement("UPDATE loans SET loan_status = ? WHERE loan_id = ?");
			update.setString(1, status);
			update.setInt(2, loanId);
			
			update.executeUpdate();

			System.out.println("Loan status updated to: " + status);
		} catch (SQLException e) {
			throw new InvalidLoanException("Error updating loan status.");
		}
	}
	
	@Override
	public List<Loan> getAllLoans() {
		List<Loan> list = new ArrayList<>();
		String query = "SELECT * FROM loans";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Loan loan = new Loan();
				loan.setLoanId(rs.getInt("loan_id"));
				
				Customer c = new Customer();
				c.setCustomerId(rs.getInt("customer_id"));
				
				loan.setCustomer(c);
				loan.setPrincipalAmount(rs.getDouble("principal_amount"));
				loan.setInterestRate(rs.getDouble("interest_rate"));
				loan.setLoanTerm(rs.getInt("loan_term"));
				loan.setLoanType(rs.getString("loan_type"));
				loan.setLoanStatus(rs.getString("loan_status"));
				
				list.add(loan);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public Loan getLoanById(int loanId) throws InvalidLoanException {
		String query = "SELECT l.*, c.name, c.email, c.phone, c.address, c.credit_score FROM loans l JOIN customers c ON l.customer_id = c.customer_id WHERE l.loan_id = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);

			ps.setInt(1, loanId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Customer c = new Customer();
				
				c.setCustomerId(rs.getInt("customer_id"));
				c.setName(rs.getString("name"));
				c.setEmail(rs.getString("email"));
				c.setPhone(rs.getString("phone"));
				c.setAddress(rs.getString("address"));
				c.setCreditScore(rs.getInt("credit_score"));

				Loan loan = new Loan();
				loan.setLoanId(rs.getInt("loan_id"));
				loan.setCustomer(c);
				loan.setPrincipalAmount(rs.getDouble("principal_amount"));
				loan.setInterestRate(rs.getDouble("interest_rate"));
				loan.setLoanTerm(rs.getInt("loan_term"));
				loan.setLoanType(rs.getString("loan_type"));
				loan.setLoanStatus(rs.getString("loan_status"));
				return loan;
			} else {
				throw new InvalidLoanException("Loan not found.");
			}
		} catch (SQLException e) {
			throw new InvalidLoanException("Error retrieving loan.");
		}
	}

	@Override
	public String getLoanStatus(int loanId) throws InvalidLoanException, SQLException {
		String status = null;
		String query = "SELECT loan_status FROM loans WHERE loan_id = ?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, loanId);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					status = rs.getString("loan_status");
				}
			}
		} catch (SQLException e) {
			throw new InvalidLoanException("Error Fetching Loan Status.");
		}
		
		return status;
	}

	@Override
	public void addCustomer(Customer customer) {
		String query = "INSERT INTO customers (name, email, phone, address, credit_score) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, customer.getName());
			ps.setString(2, customer.getEmail());
			ps.setString(3, customer.getPhone());
			ps.setString(4, customer.getAddress());
			ps.setInt(5, customer.getCreditScore());
			
			ps.executeUpdate();

			ResultSet keys = ps.getGeneratedKeys();
			if (keys.next()) {
				int id = keys.getInt(1);
				customer.setCustomerId(id);
			}

			System.out.println("Customer added successfully!\nDetails: ");
			System.out.println(customer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeCustomer(int customerId) throws CustomerNotFoundException {
		try {
			PreparedStatement loanCheck = conn.prepareStatement("SELECT COUNT(*) FROM loans WHERE customer_id = ?");
			loanCheck.setInt(1, customerId);
			
			ResultSet rs = loanCheck.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				System.out.println("Cannot remove customer with active loans.");
				return;
			}

			PreparedStatement ps = conn.prepareStatement("DELETE FROM customers WHERE customer_id = ?");
			ps.setInt(1, customerId);
			
			int rows = ps.executeUpdate();
			if (rows == 0) {
				throw new CustomerNotFoundException("Customer not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Customer getCustomerById(int customerId) throws CustomerNotFoundException {
		Customer cust = null;
		String query = "SELECT * FROM customers WHERE customer_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, customerId);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				cust = new Customer(rs.getInt("customer_id"), rs.getString("name"), rs.getString("email"), rs.getString("phone"), rs.getString("address"), rs.getInt("credit_score"));
			} else {
				throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cust;
	}

	@Override
	public List<Loan> getLoansByCustomerId(int customerId) throws CustomerNotFoundException {
		List<Loan> loans = new ArrayList<>();
		
		try {
			PreparedStatement check = conn.prepareStatement("SELECT * FROM customers WHERE customer_id = ?");
			check.setInt(1, customerId);
			
			ResultSet rsCheck = check.executeQuery();
			if (!rsCheck.next()) {
				throw new CustomerNotFoundException("Customer not found.");
			}

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM loans WHERE customer_id = ?");
			ps.setInt(1, customerId);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Loan loan = new Loan();
				loan.setLoanId(rs.getInt("loan_id"));
				
				Customer c = new Customer();
				c.setCustomerId(rs.getInt("customer_id"));
				
				loan.setCustomer(c);
				loan.setPrincipalAmount(rs.getDouble("principal_amount"));
				loan.setInterestRate(rs.getDouble("interest_rate"));
				loan.setLoanTerm(rs.getInt("loan_term"));
				loan.setLoanType(rs.getString("loan_type"));
				loan.setLoanStatus(rs.getString("loan_status"));
				
				loans.add(loan);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return loans;
	}
	
	@Override
	public void displayAllCustomerLoanMapping() {
	    String query = "SELECT c.customer_id, c.name, l.loan_id FROM customers c LEFT JOIN loans l ON c.customer_id = l.customer_id";
	    
	    try {
	    	PreparedStatement ps = conn.prepareStatement(query);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            int cid = rs.getInt("customer_id");
	            String name = rs.getString("name");
	            int loanId = rs.getInt("loan_id");

	            if (loanId > 0) {
	                try {
	                    String status = getLoanStatus(loanId);
	                    System.out.println("Customer ID: " + cid + ", Name: " + name + ", Loan ID: " + loanId + ", Status: " + status);
	                } catch (InvalidLoanException | SQLException e) {
	                    System.out.println("Customer ID: " + cid + ", Name: " + name + ", Loan ID: " + loanId + ", Status: [Unavailable]");
	                }
	            } else {
	                System.out.println("Customer ID: " + cid + ", Name: " + name + ", Loan ID: None");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	// J514 - Rishab H
}
