

public class CreditAccount extends Account {
    private double interestRate;

    public CreditAccount(Owner owner, String type, String accountNumber, String PIN) {
        super(owner, "Credit", accountNumber, PIN, 0);
        this.interestRate = 0.04;
    }

    double calculateInterest(double amount) {
        double interest;
		double interestPerDay = interestRate * (12 / 365);
		interest = amount * interestPerDay * 24;
		return interest;
	}

    @Override
    public void addBalance(double amount) {
        double newBalance;
		newBalance = this.getBalance() + amount;
		this.setBalance(newBalance);
    }

    @Override
    public void withdraw(double amount) {
        double newBalance;
		newBalance = this.getBalance() - amount;
		this.setBalance(newBalance);
    }
}
