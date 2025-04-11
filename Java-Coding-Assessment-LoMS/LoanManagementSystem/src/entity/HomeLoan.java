package entity;


public class HomeLoan extends Loan {
    private String propertyAddress;
    private double propertyValue;

    public HomeLoan() {}

    public HomeLoan(int loanId, Customer customer, double principalAmount, double interestRate, int loanTerm, String loanStatus, String propertyAddress, double propertyValue) {
        super(loanId, customer, principalAmount, interestRate, loanTerm, "Home", loanStatus);
        this.propertyAddress = propertyAddress;
        this.propertyValue = propertyValue;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public double getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(double propertyValue) {
        this.propertyValue = propertyValue;
    }

    @Override
    public String toString() {
        return super.toString() + ", Property Address: " + propertyAddress + ", Property Value: " + propertyValue;
    }
    
    // J514 - Rishab H
}
