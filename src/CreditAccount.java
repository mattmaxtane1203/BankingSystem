

public class CreditAccount extends Account {
    private double interestRate;

    public CreditAccount(Owner owner, String accountNumber, String PIN) {
        super(owner, "Credit", accountNumber, PIN, 0);
        this.interestRate = 0.04;
    }
    
    double calculateInterest(double amount) {
        double interest;
		// double interestPerDay = interestRate * (12 / 365);
		interest = amount * interestRate;
		return interest;
	}

    @Override
    public void addBalance(double amount) {
        double newBalance;
		newBalance = this.getBalance() + amount;
		this.setBalance(newBalance);
    }

    @Override
    public boolean withdraw(double amount) {
        double total_balance;
		if (amount + calculateInterest(amount) <= this.getBalance()) {
			total_balance = this.getBalance() - (amount + calculateInterest(amount));
			this.setBalance(total_balance);
			return true;
		} else {
			return false;
		}
    }
}
