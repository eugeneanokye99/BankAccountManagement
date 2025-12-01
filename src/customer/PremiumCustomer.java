package customer;

public class PremiumCustomer extends Customer {
    private final double minimumBalance;

    public PremiumCustomer(String name, int age, String contact, String address) {
        super(name, age, contact, address);
        this.minimumBalance = 10000.0; // $10,000 minimum for premium status
    }


    @Override
    public void displayCustomerDetails() {
        System.out.println("=== Premium Customer Details ===");
        System.out.println("Customer ID: " + getCustomerId());
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Contact: " + getContact());
        System.out.println("Address: " + getAddress());
        System.out.println("Type: Premium Customer");
        System.out.println("Minimum Balance Required: $" + String.format("%.2f", minimumBalance));
        System.out.println("Benefits: No monthly fees, Priority service");
    }

    @Override
    public String getCustomerType() {
        return "Premium";
    }
}